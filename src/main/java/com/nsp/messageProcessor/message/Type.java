package com.nsp.messageProcessor.message;

/**
 * This enum holds the sale types
 */
public enum Type {
     SIMPLE_SALE, // captures e.g. sale apples @ 20
     DETAIL_SALE, //captures e.g. 20 sale @10p
     ADJUSTMENT_SALE; // Add 20p sale
}