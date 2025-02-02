package com.pzen.utils;

import lombok.Data;

@Data
public class Condition {
    private String type; // e.g., "equals", "like", "gt", "lt"
    private Object value;
}
