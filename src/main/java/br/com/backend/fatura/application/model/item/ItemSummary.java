package br.com.backend.fatura.application.model.item;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Document(collection = "itemsSummary")
public class ItemSummary {

    private double totalPrice;
    private List<Item> items;
}
