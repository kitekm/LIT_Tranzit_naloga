import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.Scanner;

public class program
{
    public static void main(String[] args) throws IOException
    {
        Scanner inputName = new Scanner(System.in);
        System.out.println("Vpisi ime in priimek (brez sumnikov)");
        String vpisanImePriimek = inputName.nextLine();

        String[] splitImePriimek = vpisanImePriimek.split(" ");

        // tukaj si sestavim nastavek za url, da lahko dobim per player page, sestavljen je iz
        // prve crke priimka / prvih pet crk priimka + prvi dve crki imena + 01.html
        String nastavekZaURL="", ime="", priimek="", crka="";
        ime = splitImePriimek[0].substring(0,2).toLowerCase();

        // tukaj preverim ali je priimek krajsi od petih crk, v tem primeru za sestavljanje url-ja vzamem celoten priimek
        // uporabim se metodo tolowercase v primeru da uporabnik vpise ime in priimek z velikimi zacetnicami
        if(splitImePriimek[1].length()<5)
            priimek = splitImePriimek[1].substring(0, splitImePriimek[1].length()).toLowerCase();
        else
            priimek = splitImePriimek[1].substring(0,5).toLowerCase();
        //pridobim se prvo crko priimka
        crka = splitImePriimek[1].substring(0,1).toLowerCase();

        //sestavim url
        nastavekZaURL = crka+"/"+priimek+ime+"01.html";
        //System.out.println("nastavek za url: "+ nastavekZaURL);

        Document page = Jsoup.connect("https://www.basketball-reference.com/players/"+nastavekZaURL).get();
        Element tabela = page.getElementById("per_game");
        for(Element vrstice : tabela.select("tr.full_table"))
        {
            for(Element heading : vrstice.select("th[data-stat=season]"))
            {
                System.out.print(heading.select("a[href]").text()+" ");
            }
            for(Element tableData : vrstice.select("td[data-stat=fg3a_per_g]"))
            {
                System.out.println(tableData.select("td[data-stat=fg3a_per_g]").text());
            }
        }
    }
}
