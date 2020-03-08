<#-- @ftlvariable name="data" type="fr.eemi.margot.IndexData" -->
<html>
    <head>
        <title>Fun algorithms</title>
    </head>
    <body>
        <div class="algoChoice">Select Minimum/Maximum</div>

        <form action="/send" method="post">
            <label for="numbersInput">List of numbers</label>
            <input name="numbersInput" type="text" placeholder="Ex: 1,4,3"></input>
            <input type="submit" value="Find the maximum"></input>
        </form>

        <div class="algoChoice">Two Sum</div>
        <form action="/sending" method="post">
                    <label for="numbersInput">List of numbers</label>
                    <input name="numbersInput" type="text" placeholder="Ex: 1,4,3"></input>
                    <input type="submit" value="Find the pair"></input>
        </form>


    </body>
    <footer>
        @ Margot Rasamy - 2020
    </footer>
</html>
