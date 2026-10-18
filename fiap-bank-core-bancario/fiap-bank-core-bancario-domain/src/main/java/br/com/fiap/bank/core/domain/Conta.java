package br.com.fiap.bank.core.domain;
import java.math.BigDecimal; public record Conta(String numeroConta,String cpfCliente,String agencia,TipoConta tipoConta,BigDecimal saldo,boolean ativa) {}