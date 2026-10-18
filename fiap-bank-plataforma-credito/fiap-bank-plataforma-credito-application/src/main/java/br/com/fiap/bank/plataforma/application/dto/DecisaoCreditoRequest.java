package br.com.fiap.bank.plataforma.application.dto;
import jakarta.validation.constraints.*; public record DecisaoCreditoRequest(@NotBlank String decisao,@Size(max=500) String parecer) {}
