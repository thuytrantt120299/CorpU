package com.example.corpu.registration;

import com.example.corpu.constants.ValidateConstants;
import com.example.corpu.error.ValidationException;
import com.example.corpu.response.ApiResponse;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "api/v1/registration")
@AllArgsConstructor
public class RegistrationController {

    private final RegistrationService registrationService;
    @PostMapping()
    public ApiResponse<?> register(@RequestBody RegistrationRequest request){
        String result;
        try{
            result=registrationService.register(request);
        }catch (ValidationException e) {
            return new ApiResponse<>(400, null, e.getErrorCode(), e.getErrorMessage());
        } catch (Exception e) {
            return new ApiResponse<>(400, null, ValidateConstants.ERROR, ValidateConstants.ERROR_LABEL);
        }
        return new ApiResponse<>(200, result, null, null);
    }

    @GetMapping(path = "confirm")
    public ApiResponse<?> confirm(@RequestParam("token") String token) {
        String result;
        try{
          result =  registrationService.confirmToken(token);
        }catch (ValidationException e) {
            return new ApiResponse<>(400, null, e.getErrorCode(), e.getErrorMessage());
        } catch (Exception e) {
            return new ApiResponse<>(400, null, ValidateConstants.ERROR, ValidateConstants.ERROR_LABEL);
        }
            return new ApiResponse<>(200, result, null, null);
    }


}
