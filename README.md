# Distribution Center Management System

## Overview
The Distribution Center Management System is a hybrid application built using Spring Boot. It manages distribution centers, warehouse stock, and inventory while offering both RESTful APIs for programmatic interaction and server-rendered web pages for administrative tasks. This system is designed to optimize inventory distribution and streamline warehouse operations for logistics and retail businesses.

---

## Features

### User Management
- User registration and account creation with password encryption.
- User data is stored securely in a database.

### Inventory Management
- Add, view, update, and remove items from distribution centers.
- Track warehouse stock and replenish inventory as needed.

### Distribution Center Operations
- View and manage details of distribution centers.
- Optimize item requests by calculating distances to the closest distribution center using the Haversine formula.

### Administrative Dashboard
- A user-friendly interface for administrators to monitor and control inventory.
- Support for viewing available stock, replenishing items, and resolving inventory issues.

### RESTful APIs
- Programmatic interaction with distribution centers and inventory through REST endpoints.
- JSON responses for integration with external systems.

---

## Key Components

### Controllers
1. **LandingPageController**
   - Handles requests to the root (`/`) route.
   - Renders the `landing` page.

2. **AdminController**
   - Provides an interface for administrative tasks.
   - Features include:
     - Viewing distribution center details.
     - Requesting items from the closest center.
     - Replenishing warehouse stock.

3. **RegistrationController**
   - Manages user registration.
   - Includes password encoding and database persistence.

4. **DistController** (RESTful API)
   - Provides APIs for distribution center and item management.
   - Supports operations like:
     - Adding items to a center.
     - Removing items from a center.
     - Fetching centers and inventory.

### Repositories
1. **UserRepository**
   - Handles user data storage and retrieval.
   - Includes methods like `findByUsername`.

2. **ItemRepository**
   - Manages item data.
   - Custom queries to fetch items by brand, name, or distribution center.

3. **DistributionCenterRepository**
   - Provides access to distribution center data.
   - Includes a custom query to fetch centers with available items.

4. **WarehouseStockRepository**
   - Manages warehouse stock data.
   - Supports operations like finding stock by item.

---

## RESTful API Endpoints

### Distribution Center Management
- **Add Item to Center**
  - `POST /distributionCenters/{id}/items`
  - Request body: `Item` JSON

- **Delete Item from Center**
  - `DELETE /distributionCenters/{id}/items/{itemId}`

- **Get All Centers**
  - `GET /distributionCenters`

- **Get Center by ID**
  - `GET /distributionCenters/{id}`

- **Get Items by Brand and Name**
  - `GET /distributionCenters/items?brand={brand}&name={name}`

---

## Technologies Used
- **Backend Framework:** Spring Boot
- **Database Access:** Spring Data JPA
- **Security:** Spring Security with password encryption
- **View Rendering:** Thymeleaf (for server-rendered pages)
- **RESTful API:** JSON-based endpoints
- **Utilities:** Haversine formula for distance calculations

