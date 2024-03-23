package group.Prova1AdminMuriloMarino;

import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

@Entity
public class Restaurante {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotBlank(message = "O nome do restaurante é obrigatório")
    private String nome;
    @OneToMany(mappedBy = "restaurante")
    private List<ItemCardapio> cardapios;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public List<ItemCardapio> getCardapios() {
        return cardapios;
    }

    public void setCardapios(List<ItemCardapio> cardapios) {
        this.cardapios = cardapios;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        return ((Restaurante) o).id == (this.id);
    }
}
