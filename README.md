# <div align="center"><img src="https://github.com/Prahlad-07/CRED-Machine-Coding-Round/blob/master/CRED-Logo-Nishant-Verma.jpg?raw=true" width="200"/></div>

<div align="center">

# Smart Parking Lot Management System

[![Java](https://img.shields.io/badge/Java-8%2B-orange.svg)](https://www.oracle.com/java/)
[![License](https://img.shields.io/badge/License-MIT-blue.svg)](LICENSE)

*A production-grade Low-Level Design implementation for CRED's machine coding round*

</div>

---

## 🎯 Overview

An intelligent parking lot system supporting **multi-floor management**, **smart spot allocation**, and **thread-safe concurrent operations**.

### Key Features
- 🧠 **Smart Allocation**: Smallest spot first → Lowest floor → Deterministic ID sorting
- 🔒 **Thread-Safe**: Built with `ConcurrentHashMap` for high concurrency
- ⚡ **High Performance**: O(1) ticket lookup, O(n) optimized allocation
- 🏗️ **Clean Architecture**: SOLID principles, extensible design

---

## 📂 Project Structure

```
src/main/java/com/parkinglot/
├── enums/       # VehicleType, SpotType, SpotStatus
├── models/      # Vehicle, ParkingSpot, ParkingTicket
├── service/     # ParkingLotManager Interface & Implementation
└── Main.java    # Demo application

src/test/java/com/parkinglot/
└── ParkingLotTest.java  # Unit tests
```

---

## 🚀 Quick Start

```bash
# Clone repository
git clone https://github.com/Prahlad-07/CRED-Machine-Coding-Round.git
cd CRED-Machine-Coding-Round

# Compile
javac -d bin src/main/java/com/parkinglot/**/*.java

# Run
java -cp bin com.parkinglot.Main
```

---

## 💻 Usage

```java
// Initialize parking lot (2 floors, 10 spots each)
ParkingLotManager manager = new ParkingLotManagerImpl(2, 10);

// Park vehicle
Vehicle bike = new Vehicle("KA-01-1234", VehicleType.MOTORCYCLE);
ParkingTicket ticket = manager.parkVehicle(bike);
System.out.println("Parked: Floor " + ticket.getSpot().getFloor());

// Exit vehicle
manager.exitVehicle(ticket.getTicketId());

// Check availability
int available = manager.getAvailableSpots(0, SpotType.COMPACT);
```

---

## 🧪 Testing

```bash
mvn test  # Run all tests
```

**Coverage**: Happy path, edge cases, concurrency, boundary conditions

---

## 🎓 Design Highlights

### Smart Allocation Logic
```
Priority: Smallest Spot → Lowest Floor → Lowest Spot ID
Example: Motorcycle gets Compact/Floor-0 before Regular/Floor-1
```

### Why ConcurrentHashMap?
- Lock striping for parallel reads
- Better performance than synchronized blocks
- Multi-core processor optimized

---

## 🔮 Future Enhancements

- [ ] Payment integration with duration-based fees
- [ ] Real-time dashboard with WebSocket
- [ ] Reservation system
- [ ] REST API with Spring Boot

---

## 📧 Contact

**Prahlad** - [@Prahlad-07](https://github.com/Prahlad-07)

Project Link: [CRED-Machine-Coding-Round](https://github.com/Prahlad-07/CRED-Machine-Coding-Round)

---

<div align="center">

**⭐ Star this repo if you found it helpful!**

Made with ☕ for CRED Machine Coding Round

</div>
