package com.example.OTP_Service.Controller;

import com.example.OTP_Service.DTO.FlagResponse;
import com.example.OTP_Service.DTO.UserResponse;
import com.example.OTP_Service.DTO.otp;
import com.example.OTP_Service.Service.EmailConsumer;
import com.example.OTP_Service.Service.EmailProducer;
import com.example.OTP_Service.Service.OtpManupalation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/OTP")
public class OtpController {

    @Autowired
    private OtpManupalation otpManupalation;

    @Autowired
    private EmailConsumer emailConsumer;

    @Autowired
    private EmailProducer emailProducer;



    @PostMapping("/Validate")
    public ResponseEntity<?> validateOtp(@RequestBody otp op) {


        UserResponse userResponse = emailConsumer.getLatestUserResponse();

        if (userResponse == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("No email found. OTP cannot be validated.");
        }

        boolean isValid = otpManupalation.validateOtp(userResponse, op.getOtp());

        FlagResponse flagResponse=new FlagResponse();

        if (isValid) {
            flagResponse.setVerified(true);
            flagResponse.setEmailId(userResponse.getEmailId());
            emailProducer.sendEmailProducer(flagResponse);
           
            return ResponseEntity.ok("Valid"); 
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("Invalid or expired OTP");
    }
}
