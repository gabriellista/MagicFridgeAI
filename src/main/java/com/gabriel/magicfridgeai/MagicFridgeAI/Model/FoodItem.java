package com.gabriel.magicfridgeai.MagicFridgeAI.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDate;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.FutureOrPresent;

@Entity
@Table(name = "food_item")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

public class FoodItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O nome é obrigatório")
    private String nome;

    @NotNull(message = "A categoria é obrigatória")
    @Enumerated(EnumType.STRING)
    private CategoriaAlimento categoria;

    @NotNull(message = "A quantidade é obrigatória")
    @Positive(message = "A quantidade deve ser maior que zero")
    private Integer quantidade;

    @NotNull(message = "A unidade de medida é obrigatória")
    @Enumerated(EnumType.STRING)
    private UnidadeMedida unidade;

    @NotNull(message = "A validade é obrigatória")
    @FutureOrPresent(message = "A validade não pode estar no passado")
    private LocalDate validade;

}
