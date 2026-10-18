package br.com.fiap.bank.core.application;
import org.springframework.stereotype.Service; import org.springframework.transaction.annotation.Transactional; import java.util.*; import java.math.BigDecimal; import br.com.fiap.bank.core.domain.*; import br.com.fiap.bank.core.application.dto.*;
@Service @Transactional public class BankService {
 private final ClienteRepository clientes; private final ContaRepository contas;
 public BankService(ClienteRepository clientes,ContaRepository contas){this.clientes=clientes;this.contas=contas;}
 private String cpf(String s){return s.replaceAll("\\D","");}
 private NaoEncontradoException missing(){return new NaoEncontradoException("Recurso não encontrado.");}
 private ConflitoException conflict(String s){return new ConflitoException(s);}
 public List<ClienteResponse> clientes(){return clientes.all().stream().map(ClienteResponse::from).toList();}
 public ClienteResponse cliente(Long id){return ClienteResponse.from(clientes.byId(id).orElseThrow(this::missing));}
 public ClienteResponse clienteCpf(String cpf){return ClienteResponse.from(clientes.byCpf(cpf(cpf)).orElseThrow(this::missing));}
 public ClienteResponse criar(CriarClienteRequest r){String cpf=cpf(r.cpf());if(clientes.byCpf(cpf).isPresent())throw conflict("CPF já cadastrado.");return ClienteResponse.from(clientes.save(new Cliente(null,cpf,r.nome(),r.email(),r.dataNascimento(),r.rendaMensal())));}
 public ClienteResponse atualizar(Long id,CriarClienteRequest r){clienteDomain(id);String cpf=cpf(r.cpf());clientes.byCpf(cpf).ifPresent(c->{if(!c.id().equals(id))throw conflict("CPF já cadastrado.");});return ClienteResponse.from(clientes.save(new Cliente(id,cpf,r.nome(),r.email(),r.dataNascimento(),r.rendaMensal())));}
 private Cliente clienteDomain(Long id){return clientes.byId(id).orElseThrow(this::missing);}
 public void excluir(Long id){Cliente c=clienteDomain(id);if(!contas.byCpf(c.cpf()).isEmpty())throw conflict("Cliente possui contas vinculadas.");clientes.delete(id);}
 public List<ContaResponse> contas(){return contas.all().stream().map(ContaResponse::from).toList();}
 public ContaResponse conta(String n){return ContaResponse.from(contas.byNumero(n).orElseThrow(this::missing));}
 public ContaResponse contaCpf(String cpf){return contas.byCpf(cpf(cpf)).stream().findFirst().map(ContaResponse::from).orElseThrow(this::missing);}
 public ContaResponse abrir(CriarContaRequest r){clienteCpf(r.cpfCliente());String n=UUID.randomUUID().toString().replace("-","").substring(0,10);return ContaResponse.from(contas.save(new Conta(n,cpf(r.cpfCliente()),r.agencia()==null?"0001":r.agencia(),TipoConta.valueOf(r.tipoConta()),r.saldoInicial(),true)));}
 public ContaResponse saldo(String n,AtualizarSaldoRequest r){Conta c=contas.byNumero(n).orElseThrow(this::missing);if(!c.ativa())throw conflict("Conta inativa.");String op=r.operacao().toUpperCase(Locale.ROOT);if(!op.equals("SAQUE")&&!op.equals("DEPOSITO"))throw new RequisicaoInvalidaException("Operação deve ser SAQUE ou DEPOSITO.");BigDecimal valor=op.equals("SAQUE")?r.valor().negate():r.valor();BigDecimal novo=c.saldo().add(valor);if(novo.signum()<0)throw conflict("Saldo insuficiente.");return ContaResponse.from(contas.save(new Conta(n,c.cpfCliente(),c.agencia(),c.tipoConta(),novo,true)));}
 public void encerrar(String n){Conta c=contas.byNumero(n).orElseThrow(this::missing);if(!c.ativa())throw conflict("Conta já está encerrada.");contas.save(new Conta(n,c.cpfCliente(),c.agencia(),c.tipoConta(),c.saldo(),false));}
}
