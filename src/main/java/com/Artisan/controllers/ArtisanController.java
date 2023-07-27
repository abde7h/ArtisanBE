/*package com.Artisan.controllers;

public class ArtisanController {

}*/

package com.Artisan.controllers;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.Artisan.entities.Artisan;
import com.Artisan.services.ArtisanService;
import lombok.extern.java.Log;

@Log
@RestController
@RequestMapping(value = "/1.0.0")


public class ArtisanController {
	@Autowired(required = false)
	ArtisanService artisanService;
	@RequestMapping("/artisan")
	public List<Artisan> getArtisan(){
		log.info("Request a http://localhost:PORT/1.0.0/artisan (GET)");
		return artisanService.findAllArtisans();
		
	}
	@RequestMapping("/artisan/{idUsuario}")
	public Optional<Artisan> findArtisanById(@PathVariable Long idUsuario){
		log.info("Request a http://localhost:PORT/1.0.0/user/"+idUsuario+" (GET)");
		Optional<Artisan> artisan = artisanService.findArtisanById(idUsuario);
	 return artisan;
		
	}
	@PostMapping("/artisan/add")
	public ResponseEntity<Artisan> saveArtisan(@RequestBody Artisan artisan) {
	 log.info("Request a http://localhost:PORT/1.0.0/user/add (POST)");
	 Artisan savedArtisan = artisanService.saveArtisan(artisan);
	 if (savedArtisan != null) {
	 return ResponseEntity.status(HttpStatus.CREATED).body(savedArtisan);
	 } else {
	 return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	 }
	}
	@DeleteMapping("/artisan/delete/{idUsuario}")
	public ResponseEntity<Object> deleteUser(@PathVariable Long idUsuario){
		 log.info("Request a http://localhost:PORT/1.0.0/artisan/delete/" + idUsuario + " (DELETE)");
		 String result = artisanService.deleteArtisan(idUsuario);
		 if (result.equals("Artesano eliminado correctamente.")) {
		 return ResponseEntity.noContent().build(); 
		 } else {
		 return ResponseEntity.notFound().build();
		 }
		
	
		
	}
	@PatchMapping("/artisan/update")
	public ResponseEntity<String> updateArtisan(@RequestBody Artisan artisanUpdated) {
	 log.info("Request a http://localhost:PORT/1.0.0/user/update (PATCH)");
	 Long artisanId = (long) artisanUpdated.getId();
	 Optional<Artisan> existingArtisan = artisanService.findArtisanById(artisanId);
	 if (existingArtisan.isPresent()) {
	 artisanService.updateArtisan(artisanUpdated);
	 return ResponseEntity.ok("Artisan modificado");
	 } else {
	 return ResponseEntity.notFound().build();
	 }
	}


}

