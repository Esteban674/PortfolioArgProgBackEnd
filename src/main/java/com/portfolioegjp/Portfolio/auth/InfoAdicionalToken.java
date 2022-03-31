
package com.portfolioegjp.Portfolio.auth;

import java.util.HashMap;
import java.util.Map;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.stereotype.Component;

@Component
public class InfoAdicionalToken implements TokenEnhancer{

    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken oaat, OAuth2Authentication oaa) {
        Map<String, Object> info = new HashMap<>();
        info.put("info_adicional", "Argentina Programa - ".concat(oaa.getName()));
        ((DefaultOAuth2AccessToken) oaat).setAdditionalInformation(info);
        return oaat;
    }
    
}
