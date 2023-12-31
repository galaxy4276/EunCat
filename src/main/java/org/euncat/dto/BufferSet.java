package org.euncat.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.UUID;

@Builder
public record BufferSet(BufferedReader reader, BufferedWriter writer) {
    public void close() {
        try {
            reader.close();
            writer.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void write(String s) {
        try {
            writer.write(s);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

}
