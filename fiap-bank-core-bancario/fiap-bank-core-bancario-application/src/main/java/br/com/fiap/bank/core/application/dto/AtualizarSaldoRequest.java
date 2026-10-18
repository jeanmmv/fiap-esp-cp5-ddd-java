package br.com.fiap.bank.core.application.dto;
import jakarta.validation.constraints.*; import java.math.BigDecimal; public record AtualizarSaldoRequest(@NotBlank String operacao,@NotNull @DecimalMin(value="0.00",inclusive=false) BigDecimal valor) {}
