package atmmachine.ms;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/atms")
@CrossOrigin(origins = "http://localhost:3000/")
public class ATM_controller {

	@Autowired
	private Atm_servicepack1_impl atm_servicepack1_impl;

	@GetMapping("/load1")
	public ResponseEntity<String> getAllATM_C() {
		return new ResponseEntity<>(atm_servicepack1_impl.getAllATMs(), HttpStatus.OK);
	}
	
	@GetMapping("/load2")
	public ResponseEntity<String> getAllATM_withCodes_C() {
		return new ResponseEntity<>(atm_servicepack1_impl.getAllATMs_withCodes_(), HttpStatus.OK);
	}
	
	@GetMapping("/load3")
	public ResponseEntity<String> getConnexInfo_C() {
		return new ResponseEntity<>(atm_servicepack1_impl.getConnexInfo(), HttpStatus.OK);
	}

}
