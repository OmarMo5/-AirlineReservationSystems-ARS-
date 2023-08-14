# AirlineReservationSystems(ARS)
## ARS are systems that allow an airline to sell their inventory (seats), application allow client 
to make reservation on specific seat on airline and validate the seat availability. ARs consists of:

# Client-side application
1- Make a reservation.
2- List of available seats.
3- Register.
4- Login account.

# Server-side application
1- Receive reservation.
2- Validate the seat if available due to race condition.
3- Update available seats.
4- Must handle request using thread pool.
