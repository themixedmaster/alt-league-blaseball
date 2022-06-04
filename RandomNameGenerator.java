import java.util.*;
public class RandomNameGenerator
{
    static char[] letters = {'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'};
    static char[] consonants = {'b','c','d','f','g','h','j','k','l','m','n','p','q','r','s','t','v','w','x','y','z'};
    static char[] vowelsMinusY = {'a','e','i','o','u'};
    static char[] vowels = {'a','e','i','o','u','y'};
    static double[] englishDistribution = {8.5,2.7,4.54,3.38,11.16,1.81,2.47,3,7.54,0.2,1.1,5.49,3.01,6.65,7.16,3.17,0.2,7.58,5.74,6.95,3.63,1.01,1.29,0.29,1.78,0.27};
    static double[] englishCDistribution = {2.7,4.54,3.38,1.81,2.47,3,0.2,1.1,5.49,3.01,6.65,3.17,0.2,7.58,5.74,6.95,1.01,1.29,0.29,1.78,0.27};
    static double[] englishVDistribution = {8.5,11.16,7.54,7.16,3.63,1.78};
    static double[] englishVDistributionMinusY = {8.5,11.16,7.54,7.16,3.63};
    public static String randomName(){
        return randomName(random(1,5) + random(1,5),"" + letters[random(0,letters.length - 1)]);
    }

    public static String randomName(int length){
        return randomName(length, "" + letters[random(0,letters.length - 1)]);
    }

    public static String randomName(char startingLetter){
        return randomName(random(1,5) + random(1,5), "" + startingLetter);
    }

    public static String randomName(String startingLetters){
        return randomName(random(1,5) + random(1,5), startingLetters);
    }

    public static String randomName(int length, String startingLetters){
        if(startingLetters.length() == 0){
            startingLetters = "" + letters[random(0,letters.length - 1)];
        }
        while(length <= startingLetters.length()){
            if(startingLetters.length() >= 12){
                length = startingLetters.length() + 1;
            }else{
                length = random(1,6) + random(1,6);
            }
        }
        int mode = getMode(startingLetters, length);
        String name = capitalize(startingLetters);
        for(int x = startingLetters.length(); x < length; x++){
            char add = 'a';
            switch(mode){
                case 0:
                    add = vowels[random(0,4)];
                    if(x == length - 1){
                        add = vowels[random(0,5)];
                    }
                    /*if(x == length - 2){
                    mode = 2;
                    break;
                    }*/
                    mode++;
                    break;
                case 1:
                    add = vowels[random(0,4)];
                    mode = random(2,3);
                    break;
                case 2:
                    add = consonants[random(0, consonants.length - 1)];
                    if(x == length - 2){
                        mode = 0;
                        break;
                    }
                    mode++;
                    break;
                case 3:
                    add = consonants[random(0, consonants.length - 1)];
                    mode = random(0,1);
                    break;
            }
            name = name + add;
        }
        return name;
    }

    public static String randomNameWithDistribution(){
        return randomNameWithDistribution(random(1,4) + random(2,4),"");
    }

    public static String randomNameWithDistribution(int length){
        return randomNameWithDistribution(length,"");
    }

    public static String randomNameWithDistribution(int length, String startingLetters){
        if(startingLetters.length() == 0){
            startingLetters = "" + randomLetter(englishDistribution, letters);
        }
        while(length <= startingLetters.length()){
            if(startingLetters.length() >= 12){
                length = startingLetters.length() + 1;
            }else{
                length = random(1,6) + random(1,6);
            }
        }
        int mode = getMode(startingLetters, length);
        String name = capitalize(startingLetters);
        for(int x = startingLetters.length(); x < length; x++){
            char add = 'a';
            switch(mode){
                case 0:
                    add = randomLetter(englishVDistributionMinusY, vowelsMinusY); //0-4
                    if(x == length - 1){
                        add = randomLetter(englishVDistribution, vowels); //0-5
                    }
                    /*if(x == length - 2){
                    mode = 2;
                    break;
                    }*/
                    mode++;
                    break;
                case 1: case 2: case 3:
                    do{
                        add = randomLetter(englishVDistributionMinusY, vowelsMinusY);
                        mode = random(4,7);
                    }while(add == name.toLowerCase().charAt(name.length() - 1) && (add == 'a' || add == 'i' || add == 'u'));
                    break;
                case 4:
                    add = randomLetter(englishCDistribution, consonants);
                    if(x == length - 2){
                        mode = 0;
                        break;
                    }
                    mode++;
                    break;
                case 5: case 6: case 7:
                    add = randomLetter(englishCDistribution, consonants);
                    mode = random(0,3);
                    break;
            }
            name = name + add;
        }
        return name;
    }

    public static String unscatter(String name){
        name = name.toLowerCase();
        String simpleName = simplify(name);
        while(!isReadable(simpleName)){
            simpleName = simplify(name);
        }
        String output = "";
        for(int x = 0; x < name.length(); x++){
            if(name.charAt(x) == '-'){
                if(simpleName.charAt(x) == 'v'){
                    if(x == name.length() - 1){
                        output = output + vowels[random(0,2)];
                    }else{
                        output = output + vowels[random(0,2)];
                    }
                }else{
                    output = output + consonants[random(0, consonants.length - 1)];
                }
            }else{
                output = output + name.charAt(x);
            }
        }
        return capitalize(output);
    }

    public static String fillMiddle(String start, String end){
        return fillMiddle(start, end, random(1,5));
    }

    public static String fillMiddle(String start, String end, int add){
        String name = start;
        for(int x = 0;x < add; x++){
            name = name + '-';
        }
        return unscatter(name + end);
    }

    public static String simplify(String input){ //Gavin -> cvcvc //consonant = c, vowel = v //dashes will be randomized
        String output = "";
        for(int x = 0; x < input.length(); x++){
            if(input.charAt(x) == '-'){
                int r = random(0,1);
                if(r == 0){
                    output = output + "v";
                }else{
                    output = output + "c";
                }
            }else if(isVowel(input.charAt(x)) || (x == input.length() - 1 && input.charAt(x) == 'y')){
                output = output + "v";
            }else{
                output = output + "c";
            }
        }
        return output;
    }

    public static boolean isReadable(String input){
        int v = 0;
        int c = 0;
        for(int x = 0; x < input.length(); x++){
            if(input.charAt(x) == 'v'){
                c = 0;
                v++;
            }else{
                v = 0;
                c++;
            }
            if(c > 2 || v > 2){
                return false;
            }
        }
        //if(input.charAt(0) == input.charAt(1) || input.charAt(input.length() - 1) == input.charAt(input.length() - 2)){
        if(input.charAt(0) == input.charAt(1) || (input.charAt(input.length() - 1) == 'c' && input.charAt(input.length() - 2) == 'c')){
            return false;
        }
        return true;
    }

    public static String scatter(String input){
        return scatter(input, 0.5);
    }

    public static String scatter(String input, double scatterChance){
        String output ="";
        for(int x = 0; x < input.length(); x++){
            if(League.r.nextDouble() < scatterChance){
                if(input.charAt(x) != ' '){
                    output = output + '-';
                }else{
                    output = output + input.charAt(x);
                }
            }else{
                output = output + input.charAt(x);
            }
        }
        return output;
    }

    public static String[] split(String input){
        ArrayList<String> words = new ArrayList<String>();
        while(input.contains(" ")){
            words.add(input.substring(0,input.indexOf(" ")));
            input = input.substring(input.indexOf(" ") + 1);
        }
        words.add(input);
        String[] stringArr = new String[words.size()];
        for(int x = 0; x < words.size(); x++){
            stringArr[x] = words.get(x);
        }
        return stringArr;
    }

    public static int random(int min, int max){
        return (int)(League.r.nextDouble() * (max - min + 1)) + min;
    }

    public static String capitalize(String s){
        return s.substring(0,1).toUpperCase() + s.substring(1);
    }

    public static boolean isVowel(char c){
        if(c == 'a' || c == 'e' || c == 'i' || c == 'o' || c == 'u' || c == 'A' || c == 'E' || c == 'I' || c == 'O' || c == 'U'){
            return true;
        }
        return false;
    }

    public static int getMode(String s, int length){
        if(s.length() == 1){
            if(isVowel(s.charAt(0))){
                return random(2,3);
            }
            return random(0,3);
        }
        if(isVowel(s.charAt(s.length() - 1))){
            if(isVowel(s.charAt(s.length() - 2)) /*|| s.length() + 1 == length*/){
                return random(4,7);
            }
            int r = random(0,3);
            if(r == 0){
                return 1;
            }
            return random(4,7);
        }
        if(!isVowel(s.charAt(s.length() - 2)) || s.length() + 1 == length){
            return random(0,3);
        }
        int r = random(0,3);
        if(r == 0){
            return 3;
        }
        return random(0,3);
    }

    public static String replaceRandomLetter(String name){
        String newName = name;
        while(name.equals(newName)){
            int p = random(0,name.length() - 1);
            newName = unscatter(name.substring(0, p) + '-' + name.substring(p + 1));
        }
        return newName;
    }

    public static double arrayTotal(double[] arr){
        double total = 0;
        for(int x = 0; x < arr.length; x++){
            total = total + arr[x];
        }
        return total;
    }

    public static char randomLetter(double[] distribution, char[] letters){
        double rand = League.r.nextDouble() * arrayTotal(distribution);
        double total = 0;
        for(int x = 0; x < distribution.length; x++){
            total = total + distribution[x];
            if(total >= rand){
                return letters[x];
            }
        }
        return letters[0];
    }

}
