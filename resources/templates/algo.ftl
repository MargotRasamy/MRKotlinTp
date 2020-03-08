<#-- @ftlvariable name="data" type="fr.eemi.margot.IndexData" -->
<html>
    <head>
        <title>Fun algorithms</title>
        <link rel="stylesheet" type="text/css" href="/static/styles/style.css">
    </head>
    <body>
        <header>
            <h1>Select !</h1>
            <h3>Enter a list of integers separated by comas and submit !</h3>
        </header>

<#-- Form for each algorithms -->

        <form action="/${algoName}" method="post">
            <label for="numbersInput">List of numbers</label>
            <input name="numbersInput" type="text" placeholder="Ex: 1,4,3"></input>
            <input type="submit" value="Submit"></input>
        </form>




    </body>
    <footer>
        @ Margot Rasamy - 2020
    </footer>
</html>
