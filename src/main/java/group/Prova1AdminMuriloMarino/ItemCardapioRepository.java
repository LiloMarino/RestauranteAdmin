package group.Prova1AdminMuriloMarino;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemCardapioRepository extends JpaRepository<ItemCardapio, Integer> {
    List<ItemCardapio> findByRestauranteId(int idRestaurante);
}
