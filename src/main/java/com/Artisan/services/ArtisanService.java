/*package com.Artisan.services;

public class ArtisanService {

}*/

package com.Artisan.services;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import com.Artisan.entities.Artisan;
import com.Artisan.repository.ArtisanRepository;
@Service


public class ArtisanService {
	@Autowired
	ArtisanRepository artisanRepository;
	
	public List<Artisan> findAllArtisans() {
		return artisanRepository.findAll();
	}
	public Optional<Artisan> findArtisanById(Long id) {
		return artisanRepository.findById(id);
	}
	public Artisan saveArtisan(Artisan artisan) throws DataIntegrityViolationException {
		if (artisanRepository.findByEmail(artisan.getEmail()).isPresent()) {
			throw new DataIntegrityViolationException("El correo electrónico ya está en uso.");}
		artisanRepository.save(artisan);
		return artisan;
	}
	public String deleteArtisan(Long id) {
		if (artisanRepository.findById(id).isPresent()) {
			artisanRepository.deleteById(id);
			return "Artesano eliminado correctamente.";
		}
		return "Error! El artesano no existe";
	}
	public String updateArtisan(Artisan artisanUpdated) {
		int num = artisanUpdated.getId();
		if (artisanRepository.findById((long) num).isPresent()) {
			Artisan artisanToUpdate = new Artisan();
			artisanToUpdate.setId(artisanUpdated.getId());
			artisanToUpdate.setName(artisanUpdated.getName());
			artisanToUpdate.setEmail(artisanUpdated.getEmail());
			artisanToUpdate.setPassword(artisanUpdated.getPassword());
			artisanToUpdate.setName(artisanUpdated.getName());
			artisanToUpdate.setSurnames(artisanUpdated.getSurnames());
			artisanToUpdate.setTelephone(artisanUpdated.getTelephone());
			artisanToUpdate.setDescription(artisanUpdated.getDescription());
			artisanToUpdate.setImage(artisanUpdated.getImage());
			artisanRepository.save(artisanToUpdate);
			return "Artesano  modificado";
		}
		return "Error al modificar el Artesano";
	}


}
