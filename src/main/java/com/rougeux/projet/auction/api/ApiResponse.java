package com.rougeux.projet.auction.api;

public record ApiResponse<T>(String code, String message, T data) {}
