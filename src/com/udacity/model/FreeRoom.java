package com.udacity.model;

public class FreeRoom extends Room{

    FreeRoom(String roomNumber, Double price, RoomType roomType) {
        super(roomNumber, 0.0, roomType);
    }

    @Override
    public String toString() {
        return "FreeRoom{}";
    }
}
