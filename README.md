# Hotel-Reservation-Application

## Project Requirements

### **User Scenarios**

The application provides four user scenarios:

**Creating a customer account.** The user needs to first create a customer account before they can create a reservation.

**Searching for rooms.** The app should allow the user to search for available rooms based on provided checkin and checkout dates. If the application has available rooms for the specified date range, a list of the corresponding rooms will be displayed to the user for choosing.

**Booking a room.** Once the user has chosen a room, the app will allow them to book the room and create a reservation.

**Viewing reservations.** After booking a room, the app allows customers to view a list of all their reservations.

### **Admin Scenarios**

The application provides four administrative scenarios:
**Displaying all customers accounts.**
**Viewing all of the rooms in the hotel.**
**Viewing all of the hotel reservations.**
**Adding a room to the hotel application.**

### **Reserving a Room**

The application allows customers to reserve a room. Here are the specifics:

**Avoid conflicting reservations.** A single room may only be reserved by a single customer per a checkin and checkout date range.

**Search for recommended rooms.** If there are no available rooms for the customer's date range, a search will be performed that displays recommended rooms on alternative dates. The recommended room search will add seven days to the original checkin and checkout dates to see if the hotel has any availabilities, and then display the recommended rooms/dates to the customer.

### **Customer Requirements**

The application will have customer accounts. Each account has:

**A unique email for the customer.** RegEx is used to check that the email is in the correct format (i.e., name@domain.com).

**A first name and last name.**

The email RegEx is simple for the purpose of this exercise and may not cover all real-world valid emails. For example "name@domain.co.uk" would not be accepted by the above RegEx because it does end with ".com". If you would like to try to make your RegEx more sophisticated, you may—but it is not required for this project.

### **Error Requirements**

**The hotel reservation application handles all exceptions gracefully** (user inputs included), meaning:
No crashing. The application does not crash based on user input.

**No unhandled exceptions.** The app has try and catch blocks that are used to capture exceptions and provide useful information to the user. There are no unhandled exceptions.

