package de.ayesolutions.gogs.client;

import de.ayesolutions.gogs.client.model.AccessToken;

import java.net.URI;
import java.util.UUID;

/**
 * @author Christian Aye - c.aye@aye-solutions.de
 */
public abstract class AbstractGogsTest {

    protected final static String SSH_KEY = "ssh-rsa AAAAB3NzaC1yc2EAAAADAQABAAACAQDberous+OJrJGvyqiEKX7pH2RNHCh1XZIwzsat3nwtefGneG+9SUGNb909VCdPE2BU1zSAYOoDl/jtSIuKJ6TFbsAVgABVBnKJ44K0cdA64zGXBLFlFwPD9knZl3/okcPyXE/+TDokoQsCg8VNdipM8FiMDlfeUEfrTCXTy5qMREXBAbPqLqetQ5g8i57jH1P5rbI9nrMSpuI+mfrpPL8AiXRlZhkObLNRlNR/zhmItM3LmVKjC6MtPfz7TPVjtQaApSu1HlpvroMhR6m394l1m4wnckI+ogkcg9bY0FxkmWc0OwYGnqOcYvfv4DrCCz4rkxK1ZIHX1CGAoiBacqY4+gsQdWvYVNIUaxMeLFt/M3kT6h5k1znTXF7bEAJwJJPdOTM3Fzhx1GeUVeWQOORxYGU90ZTkrV5przAuJpbpv9GX0vAiCCq+c3u62tlv8v9X7o2PmVfDlGEUE6LPlSDAbqYI6kv/1mDJuxlsYKGFWhLiYhafswEe4Cgipw0s1WgQj//qI5+9A+9z6JlquSUcsl7elEVJaEBViRpTGye3pOaeJTnZM/aKy2B+Du+4QoRZXTZTjojTOb45DwIijLq+elVAip/B8M7ytzPFj/St/IKv7noar0kORmYYSBdaue9nqpSqJFqSyv2o67PmpN+ddqw4dgIWkL5XsypgXLHIJw== christianaye@HEIMDALL.local";

    protected final static URI API_URI = URI.create("http://helios.asnet.aye-solutions.de:13001/api/v1");

    protected final static URI API_URI_INVALID = URI.create("http://helios.asnet.aye-solutions.de:13001/apii/v1");

    protected final static GogsClient API_ADMIN = new GogsClient(API_URI, "gogs-admin", "pass", "5a855f3bcccb41275c9508552e5d8a11321c21df");

    protected final static GogsClient API_USER = new GogsClient(API_URI, "gogs-user", "pass", "486632a6c6fd07d9e4b0aad8c30c0c834d469a91");

    protected final static GogsClient API_UNAUTHORIZED = new GogsClient(API_URI);

    protected final static GogsClient API_INVALID = new GogsClient(API_URI_INVALID, new AccessToken(null, "4867d9e4b0aad8c30c0c834d469a91", "dd", "daa"));

    protected final static String USERNAME_ADMIN = "gogs-admin";

    protected final static String USERNAME_USER = "gogs-user";

    protected final static String USERNAME_UNKNOWN = "unknown";

    protected final static String GRAVATAR_URL = "https://secure.gravatar.com/avatar/";

    public AbstractGogsTest() {
        createDummyObjects();
    }

    public abstract void createDummyObjects();

    protected String generateGUID() {
        return UUID.randomUUID().toString();
    }
}
