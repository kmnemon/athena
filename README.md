# athena
### 1.description
this tool is used of visualise tech debt, there are 3 category maintenance, regulation and design.  

| Maintenance: |            | Regulation: | Design:        |
|--------------|------------|-------------|----------------|
| cyclomatic   |            | comment     | design         |
| duplication  |            | constant    | multithreading |
| godClass     | variables  | exception   | performance    |
|              | methods    | flowcontrol |                |
| superMethod  | parameters | naming      |                |
|              | lines      | oop         |                |
| godComments  |            | set         |                |
|              |            | other       |                |

### usage
* using application.yml to config the log.uri and project list to analyse the base and target project.  
* run program main.Main
* need jdk 11+

### 3.report
the report also have two area, tech debt change and tech debt limit

tech debt change means statistic two different version project increase and decrease tech debt.  
tech debt limit means depend upon base version how many tech debt increased. 

