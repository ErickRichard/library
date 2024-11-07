package br.com.uol.library.domain.common.utils;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public final class Detail {
    private String id;
    private String value;

    public Detail() {
    }

    public Detail(String id, String value) {
        this.id = id;
        this.value = value;
    }
}
