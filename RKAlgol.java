// This program demonstrates Rabin-Karp algorithm
// Writer: Ritesh Mehrotra
public class RKAlgol {

    private static int prime = 101;
    private static int num_of_chars=256; // number of characters in alphabets
    public static void main(String[] args) {
        String text = "This program demonstrates Rabin-Karp algorithm";
        String pattern = "hm";
        int index = RKAlgol.findIndex(pattern, text);
        System.out.println(index);
    }

    public static int findIndex(String pattern, String text){
        int patternLength = pattern.length();
        int textLength = text.length();
        char [] chText = text.toCharArray();
        char [] chPattern = pattern.toCharArray();
        int h=1;
        int j=0;
        boolean boolMatch = true;

        assert patternLength < textLength:"Please check input. Pattern length  > Text length";

        int patternHash = calcHash(pattern); // calculate hash of pattern
        int subTextHash = calcHash(text.substring(0, patternLength)); // calculate initial hash of subText from text
        for (int i = 0; i < patternLength-1; i++)
            h = (h*num_of_chars)%prime;

        for(int index = 0; index <= textLength - patternLength; index++) {
            //System.out.println("patternHash="+patternHash+":subTextHash="+subTextHash);
            boolMatch = true;
            if (patternHash == subTextHash )  { // hash match , now move to char by char match
                do  {
                    if (chPattern[j] != chText[index+j])
                        boolMatch = false;
                } while(boolMatch && (j++ < patternLength-1));
                if(boolMatch == true)
                    return index;
            }
            // ReHash (different from Hash
            subTextHash = calcReHash(h, subTextHash, chText[index], chText[index+patternLength]);
        }
        return -1; // index if not found
    }

    private static int calcHash(String str){
        int hash =0;
        for( char ch : str.toCharArray() ){
            hash = (num_of_chars*hash + ch)%prime;
        }
        return hash;
    }

    // rehash it using sliding window
    private static int calcReHash(int h, int subTextHash, char remove, char add){
        int hash =0;
        hash = (num_of_chars *( subTextHash - remove*h) + add)%prime; // remove= char to be remove, add=char to be added
        if (hash < 0) //handle negative values
            hash = (hash + prime);
        return hash;
    }
}
