package com.rodra.bot.dto;

import lombok.Data;

@Data
public class LeadRequest {
    private String name;
    private String phone;
    private String service;
    private String comment;
}
