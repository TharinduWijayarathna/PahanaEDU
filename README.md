# Pahana Edu

## Project Setup Guide (Eclipse)

1. **Clone or Download the Project**
   - Download the project ZIP or clone the repository:
     ```
     git clone <repository-url>
     ```

2. **Open Eclipse IDE**
   - Launch Eclipse (preferably Eclipse IDE for Enterprise Java and Web Developers).

3. **Import the Project**
   - Go to `File` > `Import...`
   - Select `Existing Projects into Workspace` under `General`.
   - Click `Next`.
   - Browse to the project root directory and select it.
   - Click `Finish`.

4. **Configure Build Path (if needed)**
   - Right-click the project > `Build Path` > `Configure Build Path`.
   - Ensure JDK 8+ is selected.
   - Add any required libraries (e.g., MySQL Connector/J for database).

5. **Set Up the Database**
   - Open `database.sql` from the project.
   - Run the SQL script in your MySQL server to create the database and tables.

6. **Configure Database Connection**
   - Edit the database connection settings in your Java source (usually in a properties file or a DAO class) to match your local MySQL credentials.

7. **Run the Project**
   - Right-click the project > `Run As` > `Run on Server` (for web projects).
   - Or run the main class if it's a standalone application.

8. **Access the Application**
   - Open your browser and go to the local server URL (e.g., `http://localhost:8080/PahanaEdu`).

---

**Note:**  
- Make sure MySQL is running.
- Default admin credentials: `admin` / `admin123` (see `database.sql`).
- For any issues, check the console output or Eclipse Problems tab.
