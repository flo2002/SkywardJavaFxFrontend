package fhv.ws22.se.skyward.domain.dtos;

import java.io.Serializable;
import java.util.UUID;

public abstract class AbstractDto implements Serializable {
    private UUID id;

    public AbstractDto() {
    }

    public UUID getId() {
        return id;
    }
    public void setId(UUID id) {
        this.id = id;
    }
}
