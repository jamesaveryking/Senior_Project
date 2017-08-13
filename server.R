

library(shiny)
library(DBI)
library(dplyr)


color_vector = c('green','red')

global_connection <- list(
  drv = RMySQL::MySQL(),
  dbname = "choice_senior_project",
  host = "160.153.96.96",
  username = "james_king",
  password = "graduation"
)

number_approves = 0
number_againsts = 0
electionName=""
unique_elections=""

convert_eID_data_Frame<-function(x)
{
  matrix_x = as.matrix(x)
  output <- vector(length=length(matrix_x))
  for (num in 0:length(matrix_x))
  {
    output[num]=matrix_x[num,1]
  }
  return (output)
}




shinyServer(function(input, output, session) {
  
  updateElections<-eventReactive(input$refresh,
                                 {
                                   update_reactive()
                                 })
  
  update_reactive<-reactive({
    local_connection <- do.call(DBI::dbConnect, global_connection)
    on.exit(DBI::dbDisconnect(local_connection))
    elections <- dbSendQuery(local_connection, "SELECT DISTINCT electionName FROM Election_Information WHERE is_active=true ORDER BY electionName ASC;")
    unique_elections = dbFetch(elections)
    dbClearResult(elections)
    
    formatted_elections=convert_eID_data_Frame(unique_elections)
    
    updateSelectInput(session,"electionName",choices = formatted_elections)
  })
  
  output$bar_plot <- renderPlot({
    update_reactive()
    
    electionName = input$electionName
    
    
    approve_sql <- sprintf("SELECT COUNT(*) FROM Active_Voters_In_Elections WHERE vote = true AND electionName = '%s'",electionName)
    against_sql <- sprintf("SELECT COUNT(*) FROM Active_Voters_In_Elections WHERE vote = false AND electionName = '%s'",electionName)
    
    local_connection <- do.call(DBI::dbConnect, global_connection)
    on.exit(DBI::dbDisconnect(local_connection))
    
    approves <- dbSendQuery(local_connection, approve_sql)
    number_approves = as.numeric(dbFetch(approves))
    dbClearResult(approves)
    againsts <- dbSendQuery(local_connection, against_sql)
    number_againsts = as.numeric(dbFetch(againsts))
    dbClearResult(againsts)
    
    if(number_approves == 0 && number_againsts==0)
    {
      plot_logical = FALSE
    }
    else
    {
      plot_logical = TRUE
    }
    
    barplot(c(number_approves,number_againsts),width = 2, space = NULL, 
            names.arg = c("Approve","Against"),
            col = color_vector,
            xlab = "Choices",
            ylab = "Number of Choices",
            axes = TRUE, border = 'gold', plot = plot_logical)
    
  })
  
  output$pie_plot <- renderPlot({
    update_reactive()
    electionName = input$electionName
    
    approve_sql <- sprintf("SELECT COUNT(*) FROM Active_Voters_In_Elections WHERE vote = true AND electionName = '%s'",electionName)
    against_sql <- sprintf("SELECT COUNT(*) FROM Active_Voters_In_Elections WHERE vote = false AND electionName = '%s'",electionName)
    
    local_connection <- do.call(DBI::dbConnect, global_connection)
    on.exit(DBI::dbDisconnect(local_connection))
    
    approves <- dbSendQuery(local_connection, approve_sql)
    number_approves = as.numeric(dbFetch(approves))
    dbClearResult(approves)
    againsts <- dbSendQuery(local_connection, against_sql)
    number_againsts = as.numeric(dbFetch(againsts))
    dbClearResult(againsts)
    
    if(number_approves != 0 && number_againsts!=0)
    {
      pie(c(number_approves,number_againsts),
          labels = c("Approve","Against"),
          edges = 490,
          radius = 1,
          init.angle = 70,
          angle = 70,
          col= c('green','red'),
          border = 'gold')
    }
    else
    {
      #Let user know that no votes have been cast
    }
   
  })
  
  
  observe({
    updateElections()
    update_reactive()
    local_connection <- do.call(DBI::dbConnect, global_connection)
    on.exit(DBI::dbDisconnect(local_connection))
    elections <- dbSendQuery(local_connection, "SELECT DISTINCT electionName FROM Election_Information WHERE is_active=true ORDER BY electionName ASC;")
    unique_elections = dbFetch(elections)
    dbClearResult(elections)

    formatted_elections=convert_eID_data_Frame(unique_elections)


    updateSelectInput(session,"electionName",choices = formatted_elections)
  })
  
})
