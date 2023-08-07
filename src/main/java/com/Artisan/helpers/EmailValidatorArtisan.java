package com.Artisan.helpers;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Component;

import com.Artisan.services.ArtisanService;


@Component
public class EmailValidatorArtisan {
	
	ArtisanService artisanService;


	public EmailValidatorArtisan(ArtisanService artisanService) {
		this.artisanService = artisanService;
		
	}
	
	
	

	private final String EMAIL_PATTERN = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";

	public boolean isValidEmail(String email) {
		Pattern pattern = Pattern.compile(EMAIL_PATTERN);
		Matcher matcher = pattern.matcher(email);
		boolean result = matcher.matches();
		return result;
	}

	public boolean checkIfExistEmail(String email) {
		boolean checkIfNotExistEmail = !artisanService.findAllArtisans().stream()
				.anyMatch(user -> user.getEmail().equalsIgnoreCase(email));
		return checkIfNotExistEmail;
	}

	public boolean checkValidAndExistEmail(String emailToValidate) {
		boolean emailIsValid = this.isValidEmail(emailToValidate);
		boolean emailNotExist = this.checkIfExistEmail(emailToValidate);

		return emailIsValid && emailNotExist ? true : false;
	}

    public static void main(String[] args) {
        String[] testEmails = {
            "john.doe@example.com",
            "jane.doe@subdomain.example.co.uk",
            "invalid_email",
            "user@example",
            "@example.com",
            "user@.com",
            "hola@gmail.com",
            "emilio.s@example.com"
        };

        for (String email : testEmails) {
         EmailValidatorUser emailValidator = new EmailValidatorUser(null);
 boolean isValid = emailValidator.isValidEmail(email);
            System.out.println(email + ": " + isValid);
        }
    }
}