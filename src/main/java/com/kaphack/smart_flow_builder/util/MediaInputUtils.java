package com.kaphack.smart_flow_builder.util;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Base64;

@Component
public class MediaInputUtils {

  public Resource getResourceFromImageUrl(String imageUrl) {
    try {
      // Use RestTemplate to fetch the image
      RestTemplate restTemplate = new RestTemplate();
      ResponseEntity<byte[]> response = restTemplate.getForEntity(imageUrl, byte[].class);

      if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
        // Wrap the byte array in a ByteArrayResource
        return new ByteArrayResource(response.getBody());
      } else {
        throw new RuntimeException("Failed to fetch image. HTTP Status: " + response.getStatusCode());
      }
    } catch (Exception e) {
      throw new RuntimeException("Error occurred while fetching resource: " + e.getMessage(), e);
    }
  }

  public String getBase64FromImageUrl(String imageUrl) {
    try {
      // Create a RestTemplate instance to fetch the image
      RestTemplate restTemplate = new RestTemplate();

      // Fetch the image as byte array
      ResponseEntity<byte[]> response = restTemplate.getForEntity(imageUrl, byte[].class);

      if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
        // Convert the image bytes to Base64 string
        byte[] imageBytes = response.getBody();
        return Base64.getEncoder().encodeToString(imageBytes);
      } else {
        throw new RuntimeException("Failed to fetch image. HTTP Status: " + response.getStatusCode());
      }
    } catch (Exception e) {
      throw new RuntimeException("Error occurred while converting image to Base64: " + e.getMessage(), e);
    }
  }
}
