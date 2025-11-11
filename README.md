# Employee Tracker

This is a Java-based web application for tracking employee tasks.

## Project Details

*   **Database:** `trackerdb`
*   **Database User:** `pept_user`

## How to Run the Project

### 1. Database Setup

*   Make sure you have a MySQL database named `trackerdb`.
*   Create a user named `pept_user` with privileges for the `trackerdb` database.
*   Run the SQL script `EmployeeTracker_secure/sql/create_admin_bcrypt.sql` to create the initial admin user.

### 2. Build the Project

*   Navigate to the `EmployeeTracker_secure` directory in your VS Code terminal.
*   Run the following Maven command to build the project. This will create a `.war` file in the `target` directory.

    ```bash
    mvn clean install
    ```

### 3. Deploy the Application

*   **Install Apache Tomcat:** If you don't have it already, download and install Apache Tomcat.
*   **Copy the .war file:** Copy the generated `EmployeeTracker.war` file from the `EmployeeTracker_secure/target` directory to the `webapps` directory of your Tomcat installation.
*   **Start Tomcat:** Start the Tomcat server. You can usually do this by running the `startup.bat` script in the `bin` directory of your Tomcat installation.

### 4. Accessing the Application

Once the application is deployed, you can access it in your web browser at the following URL:

*   [http://localhost:8080/EmployeeTracker/](http://localhost:8080/EmployeeTracker/)

**Note:** The port number `8080` is the default for Apache Tomcat. If you have configured Tomcat to run on a different port, please adjust the URL accordingly.

## Login Credentials

*   **Admin Email:** `admin@gmail.com`
*   **Admin Password:** `adminyash18`

## Running in VS Code on Windows

1.  **Open the Terminal:** Open the integrated terminal in VS Code by going to `View` > `Terminal`.
2.  **Navigate to the Project Directory:** Use the `cd` command to navigate to the `EmployeeTracker_secure` directory.

    ```bash
    cd EmployeeTracker_secure
    ```

3.  **Build the Project:** Run the following Maven command to build the project:

    ```bash
    mvn clean install
    ```

4.  **Deploy and Run:** Follow the deployment instructions in section 3 to deploy the `.war` file to Tomcat and run the application.
