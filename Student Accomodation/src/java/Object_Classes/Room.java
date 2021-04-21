/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Object_Classes;

/**
 *
 * @author Mundia
 */
public class Room {
    private String room_id,room_type,room_location,room_payment,room_status;
    private int room_charges,id;

    /**
     * @return the room_id
     */
    public String getRoom_id() {
        return room_id;
    }

    /**
     * @param room_id the room_id to set
     */
    public void setRoom_id(String room_id) {
        this.room_id = room_id;
    }

    /**
     * @return the room_type
     */
    public String getRoom_type() {
        return room_type;
    }

    /**
     * @param room_type the room_type to set
     */
    public void setRoom_type(String room_type) {
        this.room_type = room_type;
    }

    /**
     * @return the room_location
     */
    public String getRoom_location() {
        return room_location;
    }

    /**
     * @param room_location the room_location to set
     */
    public void setRoom_location(String room_location) {
        this.room_location = room_location;
    }

    /**
     * @return the room_payment
     */
    public String getRoom_payment() {
        return room_payment;
    }

    /**
     * @param room_payment the room_payment to set
     */
    public void setRoom_payment(String room_payment) {
        this.room_payment = room_payment;
    }

    /**
     * @return the room_status
     */
    public String getRoom_status() {
        return room_status;
    }

    /**
     * @param room_status the room_status to set
     */
    public void setRoom_status(String room_status) {
        this.room_status = room_status;
    }

    /**
     * @return the room_charges
     */
    public int getRoom_charges() {
        return room_charges;
    }

    /**
     * @param room_charges the room_charges to set
     */
    public void setRoom_charges(int room_charges) {
        this.room_charges = room_charges;
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }
}
