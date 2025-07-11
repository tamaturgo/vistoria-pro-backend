package com.tamaturgo.provistoria.dto.property;

import com.tamaturgo.provistoria.dto.client.ClientDTO;

import java.util.List;

public record PropertyRequest(
        String name,
        String address,
        String number,
        String complement,
        String type,
        String block,
        String tower,
        List<String> tags,
        ClientDTO client
) {}