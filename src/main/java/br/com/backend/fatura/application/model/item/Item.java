package br.com.backend.fatura.application.model.item;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Document(collection = "items")
public class Item {

    @Id
    @JsonSerialize(using = ToStringSerializer.class)
    private ObjectId id;
    private String date;
    @NotBlank(message = "A descrição não pode ser vazio")
    private String description;
    @NotBlank(message = "O valor não pode ser vazio")
    private double value;
}
