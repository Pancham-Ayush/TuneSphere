package com.example.MIcroService_Player.Model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SongMeta {

    private String id;
    private String path;
    private long size;
}