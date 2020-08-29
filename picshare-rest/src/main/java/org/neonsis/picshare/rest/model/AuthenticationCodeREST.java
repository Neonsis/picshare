package org.neonsis.picshare.rest.model;

import javax.xml.bind.annotation.XmlType;

@XmlType(name = "")
public class AuthenticationCodeREST {

    private String code;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
