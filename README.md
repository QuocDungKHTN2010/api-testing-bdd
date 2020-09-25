# api-testing-bdd
API Testing Framework
1. Create Maven Project with Cucumber and Rest Assure Dependencies
    Dependencies:  
        1. Run test: Junit, cucumber-junit 
        2. Build request: Rest-assured
        3. DSL language: cucumber-java, cucumber-jvm-deps, 
        4. Get value in Json file: json-path, 
        5. Parse Json to Object: gson
2. Define Project Structure with Cucumber Framework Setup
3. Create Feature File with you want to Automate
4. Implement Smart Step Definition files with supported code
5. Build Utils File to define all reusable request and response specifications
6. Build Pojo classes for Serializing and Deserialing Json Payload
7. Define Global Properties and drive all the global variables from Properties file
8. Define Enum class with constants to centralize all resources detail
9. Implement Data driven Mechanism to drive data dynamically from Feature files
10. Implement Parameterization to run tests with multiple data sets using Cucumber Example keywork
11. Add More Tests and implement Tagging Mechanism to run selected Tests frn Test Runner file
12. Implement Pre and Postconditions for test with Cucumber Hooks
13. Execute Complete Framework from Maven Commands
14. Implement Maven driven global values into Test for dynamic execution
15. Generate Reports for Test Execution result
16. Integrate the framework into Jenkin CI/CD tool
17. Implement Parameterized Jenkin job to choose the global values at run time
