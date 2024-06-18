package dm_hangman.state;

import java.io.*;
import java.util.*;

import static java.util.Map.entry;

public final class InputHelper {
    private static final String OUTPUT_TXT = "scores.txt";

    private static final DoctrinalMasteryDictionary englishBookOfMormon = new DoctrinalMasteryDictionary(Map.ofEntries(
            entry("1 Nephi 3:7", "And it came to pass that I, Nephi, said unto my father: I will go and do the things which the Lord hath commanded, for I know that the Lord giveth no commandments unto the children of men, save he shall prepare a way for them that they may accomplish the thing which he commandeth them."),
            entry("2 Nephi 2:25", "Adam fell that men might be; and men are, that they might have joy."),
            entry("2 Nephi 2:27", "Wherefore, men are free according to the flesh; and all things are given them which are expedient unto man. And they are free to choose liberty and eternal life, through the great Mediator of all men, or to choose captivity and death, according to the captivity and power of the devil; for he seeketh that all men might be miserable like unto himself."),
            entry("2 Nephi 26:33", "For none of these iniquities come of the Lord; for he doeth that which is good among the children of men; and he doeth nothing save it be plain unto the children of men; and he inviteth them all to come unto him and partake of his goodness; and he denieth none that come unto him, black and white, bond and free, male and female; and he remembereth the heathen; and all are alike unto God, both Jew and Gentile."),
            entry("2 Nephi 28:30", "For behold, thus saith the Lord God: I will give unto the children of men line upon line, precept upon precept, here a little and there a little; and blessed are those who hearken unto my precepts, and lend an ear unto my counsel, for they shall learn wisdom; for unto him that receiveth I will give more; and from them that shall say, We have enough, from them shall be taken away even that which they have."),
            entry("2 Nephi 32:3", "Angels speak by the power of the Holy Ghost; wherefore, they speak the words of Christ. Wherefore, I said unto you, feast upon the words of Christ; for behold, the words of Christ will tell you all things what ye should do."),
            entry("2 Nephi 32:8-9", "And now, my beloved brethren, I perceive that ye ponder still in your hearts; and it grieveth me that I must speak concerning this thing. For if ye would hearken unto the Spirit which teacheth a man to pray, ye would know that ye must pray; for the evil spirit teacheth not a man to pray, but teacheth him that he must not pray.\n\nBut behold, I say unto you that ye must pray always, and not faint; that ye must not perform any thing unto the Lord save in the first place ye shall pray unto the Father in the name of Christ, that he will consecrate thy performance unto thee, that thy performance may be for the welfare of thy soul."),
            entry("Mosiah 2:17", "And behold, I tell you these things that ye may learn wisdom; that ye may learn that when ye are in the service of your fellow beings ye are only in the service of your God."),
            entry("Mosiah 2:41", "And moreover, I would desire that ye should consider on the blessed and happy state of those that keep the commandments of God. For behold, they are blessed in all things, both temporal and spiritual; and if they hold out faithful to the end they are received into heaven, that thereby they may dwell with God in a state of never-ending happiness. O remember, remember that these things are true; for the Lord God hath spoken it."),
            entry("Mosiah 3:19", "For the natural man is an enemy to God, and has been from the fall of Adam, and will be, forever and ever, unless he yields to the enticings of the Holy Spirit, and putteth off the natural man and becometh a saint through the atonement of Christ the Lord, and becometh as a child, submissive, meek, humble, patient, full of love, willing to submit to all things which the Lord seeth fit to inflict upon him, even as a child doth submit to his father."),
            entry("Mosiah 4:9", "Believe in God; believe that he is, and that he created all things, both in heaven and in earth; believe that he has all wisdom, and all power, both in heaven and in earth; believe that man doth not comprehend all the things which the Lord can comprehend."),
            entry("Mosiah 18:8-10", "And it came to pass that he said unto them: Behold, here are the waters of Mormon (for thus were they called) and now, as ye are desirous to come into the fold of God, and to be called his people, and are willing to bear one another’s burdens, that they may be light;\n\nYea, and are willing to mourn with those that mourn; yea, and comfort those that stand in need of comfort, and to stand as witnesses of God at all times and in all things, and in all places that ye may be in, even until death, that ye may be redeemed of God, and be numbered with those of the first resurrection, that ye may have eternal life—\n\nNow I say unto you, if this be the desire of your hearts, what have you against being baptized in the name of the Lord, as a witness before him that ye have entered into a covenant with him, that ye will serve him and keep his commandments, that he may pour out his Spirit more abundantly upon you?")
    ));

    private static final DoctrinalMasteryDictionary portugueseBookOfMormon = new DoctrinalMasteryDictionary(Map.ofEntries(
            entry("1 Néfi 3:7", "E aconteceu que eu, Néfi, disse a meu pai: Eu irei e cumprirei as ordens do Senhor, porque sei que o Senhor nunca dá ordens aos filhos dos homens sem antes preparar um caminho pelo qual suas ordens possam ser cumpridas."),
            entry("2 Néfi 2:25", "Adão caiu para que os homens existissem; e os homens existem para que tenham alegria."),
            entry("2 Néfi 2:27", "Portanto, os homens são livres segundo a carne; e todas as coisas de que necessitam lhes são dadas. E são livres para escolher a liberdade e a vida eterna por meio do grande Mediador de todos os homens, ou para escolherem o cativeiro e a morte, de acordo com o cativeiro e o poder do diabo; pois ele procura tornar todos os homens tão miseráveis como ele próprio."),
            entry("2 Néfi 26:33", "Pois nenhuma destas iniquidades vem do Senhor, porque ele faz o que é bom para os filhos dos homens; e não faz coisa alguma que não seja clara para os filhos dos homens; e convida todos a virem a ele e a participarem de sua bondade; e não repudia quem quer que o procure, negro e branco, escravo e livre, homem e mulher; e lembra-se dos pagãos; e todos são iguais perante Deus, tanto judeus como gentios."),
            entry("2 Néfi 28:30", "Pois eis que assim diz o Senhor Deus: Darei aos filhos dos homens linha sobre linha, preceito sobre preceito, um pouco aqui e um pouco ali; e abençoados os que dão ouvidos aos meus preceitos e escutam os meus conselhos, porque obterão sabedoria; pois a quem recebe darei mais; e dos que disserem: Temos o suficiente, destes será tirado até mesmo o que tiverem."),
            entry("2 Néfi 32:3", "Os anjos falam pelo poder do Espírito Santo; falam, portanto, as palavras de Cristo. Por isto eu vos disse: Banqueteai-vos com as palavras de Cristo; pois eis que as palavras de Cristo vos dirão todas as coisas que deveis fazer."),
            entry("2 Néfi 32:8-9", "E agora, meus amados irmãos, percebo que ainda meditais em vosso coração; e é-me doloroso falar-vos sobre isso. Porque, se désseis ouvidos ao Espírito que ensina o homem a orar, saberíeis que deveis orar; porque o espírito mau não ensina o homem a orar, mas ensina-lhe que não deve orar.\n\nMas eis que vos digo que deveis orar sempre e não desfalecer; e nada deveis fazer para o Senhor sem antes orar ao Pai, em nome de Cristo, para que ele consagre para vós a vossa ação, a fim de que a vossa ação seja para o bem-estar de vossa alma."),
            entry("Mosias 2:17", "E eis que vos digo estas coisas para que aprendais sabedoria; para que saibais que, quando estais a serviço de vosso próximo, estais somente a serviço de vosso Deus."),
            entry("Mosias 2:41", "E ainda mais, quisera que considerásseis o estado abençoado e feliz daqueles que guardam os mandamentos de Deus. Pois eis que são abençoados em todas as coisas, tanto materiais como espirituais; e se eles se conservarem fiéis até o fim, serão recebidos no céu, para que assim possam habitar com Deus em um estado de felicidade sem fim. Oh! Lembrai-vos, lembrai-vos de que estas coisas são verdadeiras, porque o Senhor Deus as disse."),
            entry("Mosias 3:19", "Porque o homem natural é inimigo de Deus e tem-no sido desde a queda de Adão e sê-lo-á para sempre; a não ser que ceda ao influxo do Santo Espírito e despoje-se do homem natural e torne-se santo pela expiação de Cristo, o Senhor; e torne-se como uma criança, submisso, manso, humilde, paciente, cheio de amor, disposto a submeter-se a tudo quanto o Senhor achar que lhe deva infligir, assim como uma criança se submete a seu pai."),
            entry("Mosias 4:9", "Acreditai em Deus; acreditai que ele existe e que criou todas as coisas, tanto no céu como na Terra; acreditai que ele tem toda a sabedoria e todo o poder, tanto no céu como na Terra; acreditai que o homem não compreende todas as coisas que o Senhor pode compreender."),
            entry("Mosias 18:8-10", "E aconteceu que ele lhes disse: Eis aqui as águas de Mórmon (pois assim eram chamadas); e agora, sendo que desejais entrar no rebanho de Deus e ser chamados seu povo; e sendo que estais dispostos a carregar os fardos uns dos outros, para que fiquem leves;\n\nSim, e estais dispostos a chorar com os que choram; sim, e consolar os que necessitam de consolo e servir de testemunhas de Deus em todos os momentos e em todas as coisas e em todos os lugares em que vos encontreis, mesmo até a morte; para que sejais redimidos por Deus e contados com os da primeira ressurreição, para que tenhais a vida eterna —\n\nAgora vos digo que, se for esse o desejo de vosso coração, o que vos impede de serdes batizados em nome do Senhor, como um testemunho, perante ele, de que haveis feito convênio com ele de servi-lo e guardar seus mandamentos, para que ele possa derramar seu Espírito com mais abundância sobre vós?")
    ));

    public static final String ENGLISH = "en";
    public static final String PORTUGUESE = "pt";
    private static final Map<String, DoctrinalMasteryDictionary> languageMap = Map.ofEntries(
            entry(ENGLISH, englishBookOfMormon),
            entry(PORTUGUESE, portugueseBookOfMormon)
    );

    private final DoctrinalMasteryDictionary dmDictionary;
    private final Random rand = new Random();
    public InputHelper(String lang){
        this.dmDictionary = languageMap.get(lang);
    }

    public String getReferenceInput(){
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

        Map<ScoreAndTime, String> topScores = readLeaderboard(reference);
        System.out.println("************** LEADERBOARD **************");
        int i = 0;
        for(Map.Entry<ScoreAndTime, String> kvp : topScores.entrySet()) {
            i++;
            if (i > 10) {
                return;
            }
            String paddedScore = "" + kvp.getKey().score;
            for(int j = paddedScore.length(); j < 7; ++j){
                paddedScore = "0" + paddedScore;
            }
            String paddedRank = i > 9 ? "" + i : "0" + i;

            System.out.printf("%s. %s\t%s\t%s%n", paddedRank, paddedScore, kvp.getKey().time, kvp.getValue());
        }
    }

    private static Map<ScoreAndTime, String> readLeaderboard(String reference) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(OUTPUT_TXT))) {
            String line;
            Map<ScoreAndTime, String> topScores = new TreeMap<>();
            while ((line = reader.readLine()) != null) {
                // Lines have the schema "REFERENCE, TIME, SCORE, NAME"
                // So, a typical line might look like:
                //              1 Nephi 3:7, 00:34.622, 50625, BR HYER
                if (!line.startsWith(reference)) {
                    continue;
                }

                String[] splitLine = line.split(", ");
                int score = Integer.parseInt(splitLine[2]);

                topScores.put(new ScoreAndTime(score, splitLine[1]), splitLine[3]);
            }
            return topScores;
        }
    }

    private record ScoreAndTime(int score, String time) implements Comparable<ScoreAndTime> {
        @Override
            public int compareTo(ScoreAndTime obj) {
                int output = obj.score - this.score; // negative if this score is higher, since we want higher scores to appear first
                if (output != 0) {
                    return output;
                }

                return this.time.compareTo(obj.time); // note: faster times alphabetically come first
            }

            @Override
            public boolean equals(Object obj) {
                if (obj == null) {
                    return false;
                }

                if (obj.getClass() != this.getClass()) {
                    return false;
                }

                final ScoreAndTime other = (ScoreAndTime) obj;
                return this.score == other.score && this.time.equals(other.time);
            }
        }
}
