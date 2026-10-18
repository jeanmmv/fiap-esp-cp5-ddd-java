package br.com.fiap.bank.plataforma.infrastructure.client;
import br.com.fiap.bank.plataforma.domain.ScrGateway; import org.springframework.stereotype.Component; @Component public class ScrGatewayAdapter implements ScrGateway { public boolean possuiRestricao(String cpf){return cpf.endsWith("9");} }
