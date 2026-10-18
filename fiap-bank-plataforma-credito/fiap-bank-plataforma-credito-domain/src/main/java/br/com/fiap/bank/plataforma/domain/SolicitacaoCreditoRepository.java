package br.com.fiap.bank.plataforma.domain;
import java.util.*; public interface SolicitacaoCreditoRepository { SolicitacaoCredito save(SolicitacaoCredito solicitacao); Optional<SolicitacaoCredito> findById(Long id); List<SolicitacaoCredito> findAll(); List<SolicitacaoCredito> findByCpf(String cpf); }
