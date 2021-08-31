package dan.jms.dangateway.security;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

public class CustomJwtAuthenticationConverter extends JwtAuthenticationConverter {
    public static final String RESOURCE_ACCESS = "resource_access";

    protected Collection<GrantedAuthority> extractAuthorities(Jwt jwt) {
        Map<String, Object> clientRoles = jwt.getClaimAsMap(RESOURCE_ACCESS);
        ObjectMapper mapper = new ObjectMapper();
        ArrayList<GrantedAuthority> permissions = new ArrayList<>();
        JsonNode actualObject;

        try {
            actualObject = mapper.readTree(clientRoles.get("dan-client").toString());
            ArrayNode roles = (ArrayNode) actualObject.get("roles");
            roles.forEach(role -> permissions.add(new SimpleGrantedAuthority(role.asText().toString())));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return permissions;
    }
}
