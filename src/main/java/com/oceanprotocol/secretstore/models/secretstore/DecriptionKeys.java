package com.oceanprotocol.secretstore.models.secretstore;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DecriptionKeys {

    @JsonProperty
    public String decrypted_secret;

    @JsonProperty
    public String common_point;

    @JsonProperty
    public List<String> decrypt_shadows;


    // Reduced constructor
    public DecriptionKeys() {
        this.decrypted_secret= null;
        this.common_point= null;
        this.decrypt_shadows= new ArrayList<>();
    }

    public DecriptionKeys(String decrypted_secret, String common_point, ArrayList<String> decrypt_shadows) {
        this.decrypted_secret = decrypted_secret;
        this.common_point = common_point;
        this.decrypt_shadows = decrypt_shadows;
    }

    public static DecriptionKeys builder(ObjectMapper mapper, String json) throws IOException {
        return mapper.readValue(json, DecriptionKeys.class);

    }

    public String getDecryptedSecret() {
        return decrypted_secret;
    }

    public void setDecryptedSecret(String decrypted_secret) {
        this.decrypted_secret = decrypted_secret;
    }

    public String getCommonPoint() {
        return common_point;
    }

    public void setCommonPoint(String common_point) {
        this.common_point = common_point;
    }

    public List<String> getDecryptShadows() {
        return decrypt_shadows;
    }

    public void setDecryptShadows(List<String> decrypt_shadows) {
        this.decrypt_shadows = decrypt_shadows;
    }

    @Override
    public String toString() {
        return "DecriptionKeys{" +
                "decrypted_secret='" + decrypted_secret + '\'' +
                ", common_point='" + common_point + '\'' +
                ", decrypt_shadows=" + decrypt_shadows +
                '}';
    }
}
