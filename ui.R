
# This is the user-interface definition of a Shiny web application.
# You can find out more about building applications with Shiny here:
#
# http://shiny.rstudio.com
#

library(shiny)
library(gridExtra)
library(DBI)




shinyUI(fluidPage(
  
  
  titlePanel("Election Results"),
  sidebarLayout(position = "left",
                sidebarPanel(
                              selectInput("electionName", label = h1("Select Election"),choices = list("sample")),
                              actionButton("refresh","Refresh")
                              
                            ), 
                  
                  mainPanel("Results: (Blank means no votes yet cast) ",
                            fluidRow(
                              splitLayout(cellWidths = c("35%", "65%"), plotOutput("bar_plot"), plotOutput("pie_plot"))
                            )
                  )
                )
  )
)