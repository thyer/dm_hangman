package dm_hangman.state;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

import static java.util.Map.entry;

public final class InputHelper {
    private static final String OUTPUT_TXT = "scores.txt";

    private static final DoctrinalMasteryDictionary englishBookOfMormon = new DoctrinalMasteryDictionary(Map.ofEntries(
            entry("test", "test"),
            entry("1 Nephi 3:7", "And it came to pass that I, Nephi, said unto my father: I will go and do the things which the Lord hath commanded, for I know that the Lord giveth no commandments unto the children of men, save he shall prepare a way for them that they may accomplish the thing which he commandeth them."),
            entry("2 Nephi 2:25", "Adam fell that men might be; and men are, that they might have joy."),
            entry("2 Nephi 2:27", "Wherefore, men are free according to the flesh; and all things are given them which are expedient unto man. And they are free to choose liberty and eternal life, through the great Mediator of all men, or to choose captivity and death, according to the captivity and power of the devil; for he seeketh that all men might be miserable like unto himself."),
            entry("2 Nephi 26:33", "For none of these iniquities come of the Lord; for he doeth that which is good among the children of men; and he doeth nothing save it be plain unto the children of men; and he inviteth them all to come unto him and partake of his goodness; and he denieth none that come unto him, black and white, bond and free, male and female; and he remembereth the heathen; and all are alike unto God, both Jew and Gentile."),
            entry("2 Nephi 28:30", "For behold, thus saith the Lord God: I will give unto the children of men line upon line, precept upon precept, here a little and there a little; and blessed are those who hearken unto my precepts, and lend an ear unto my counsel, for they shall learn wisdom; for unto him that receiveth I will give more; and from them that shall say, We have enough, from them shall be taken away even that which they have."),
            entry("2 Nephi 32:3", "Angels speak by the power of the Holy Ghost; wherefore, they speak the words of Christ. Wherefore, I said unto you, feast upon the words of Christ; for behold, the words of Christ will tell you all things what ye should do."),
            entry("2 Nephi 32:8-9", "And now, my beloved brethren, I perceive that ye ponder still in your hearts; and it grieveth me that I must speak concerning this thing. For if ye would hearken unto the Spirit which teacheth a man to pray, ye would know that ye must pray; for the evil spirit teacheth not a man to pray, but teacheth him that he must not pray.\n\nmBut behold, I say unto you that ye must pray always, and not faint; that ye must not perform any thing unto the Lord save in the first place ye shall pray unto the Father in the name of Christ, that he will consecrate thy performance unto thee, that thy performance may be for the welfare of thy soul."),
            entry("Mosiah 2:17", "And behold, I tell you these things that ye may learn wisdom; that ye may learn that when ye are in the service of your fellow beings ye are only in the service of your God."),
            entry("Mosiah 2:41", "And moreover, I would desire that ye should consider on the blessed and happy state of those that keep the commandments of God. For behold, they are blessed in all things, both temporal and spiritual; and if they hold out faithful to the end they are received into heaven, that thereby they may dwell with God in a state of never-ending happiness. O remember, remember that these things are true; for the Lord God hath spoken it."),
            entry("Mosiah 3:19", "For the natural man is an enemy to God, and has been from the fall of Adam, and will be, forever and ever, unless he yields to the enticings of the Holy Spirit, and putteth off the natural man and becometh a saint through the atonement of Christ the Lord, and becometh as a child, submissive, meek, humble, patient, full of love, willing to submit to all things which the Lord seeth fit to inflict upon him, even as a child doth submit to his father."),
            entry("Mosiah 4:9", "Believe in God; believe that he is, and that he created all things, both in heaven and in earth; believe that he has all wisdom, and all power, both in heaven and in earth; believe that man doth not comprehend all the things which the Lord can comprehend."),
            entry("Mosiah 18:8-10", "And it came to pass that he said unto them: Behold, here are the waters of Mormon (for thus were they called) and now, as ye are desirous to come into the fold of God, and to be called his people, and are willing to bear one another’s burdens, that they may be light;\n\nYea, and are willing to mourn with those that mourn; yea, and comfort those that stand in need of comfort, and to stand as witnesses of God at all times and in all things, and in all places that ye may be in, even until death, that ye may be redeemed of God, and be numbered with those of the first resurrection, that ye may have eternal life—\n\nNow I say unto you, if this be the desire of your hearts, what have you against being baptized in the name of the Lord, as a witness before him that ye have entered into a covenant with him, that ye will serve him and keep his commandments, that he may pour out his Spirit more abundantly upon you?")
    ));

    public static final String ENGLISH = "en";
    private static final Map<String, DoctrinalMasteryDictionary> languageMap = Map.ofEntries(
            entry(ENGLISH, englishBookOfMormon)
    );

    private final DoctrinalMasteryDictionary dmDictionary;
    public InputHelper(String lang){
        this.dmDictionary = languageMap.get(lang);
    }

    public String getReferenceInput() {
        Scanner scanner = new Scanner(System.in);
        int i = 0;
        ArrayList<String> referencesList = new ArrayList<>(dmDictionary.getReferences());
        Collections.sort(referencesList);
        for(String reference : referencesList){
            System.out.printf("[%d]:\t%s%n", i, reference);
            i++;
        }
        System.out.println("Please pick a DM to guess: ");


        int index = -1;
        while(index < 0){
            try{
                String input = scanner.nextLine();
                index = Integer.parseInt(input);
            }
            catch(Exception e){
                System.out.println("Your input was not a number (0, 1, 2, ...). Please input a number: ");
            }
        }

        return referencesList.get(index);
    }

    public String getDM(String reference) {
        return dmDictionary.getDM(reference);
    }

    public void recordName(String reference, String time, int points) throws IOException {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter your name to be recorded: ");
        String name = scanner.nextLine().toUpperCase();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(OUTPUT_TXT, true))) {
            writer.append(String.format("%s, %s, %d, %s%n", reference, time, points, name));
        }
    }
}
