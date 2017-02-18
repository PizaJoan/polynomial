import java.util.Arrays;

public class Polynomial {
    float[] nums = {0};

    // Constructor per defecte. Genera un polinomi zero
    public Polynomial() {
    }

    // Constructor a partir dels coeficients del polinomi en forma d'array
    public Polynomial(float[] cfs) {
        //Afegim al array defloats que ens passen al array del propi polinomi
        this.nums = new float[cfs.length];
        for (int i = 0; i < cfs.length; i++) {
            //Amb aquest constructor no deixarem que tengui cap cero a la part esquerra, sempre tendrà que començar per
            //un numero diferent a zero
            if (cfs[0] == 0) {
                for (int j = 0; j < cfs.length; j++) {
                    //Quan trobam on hi ha un numero diferent a zero realment es quan cream el array be i despres li
                    //ficam tots els valors que conté
                    if (cfs[j] != 0) {
                        //Crearem un array de la longitud original menys j ja que j serà allà on es troba el nombre
                        //Diferent a zero
                        this.nums = new float[cfs.length - j];
                        for (int k = 0; k < cfs.length - j; k++) {
                            this.nums[k] = cfs[k + j];
                        }
                        break;
                    }
                }
                //En el cas que no contengui ceros al principi simplement el copia tal cual
            } else {
                this.nums[i] = cfs[i];
            }
        }
    }

    // Constructor a partir d'un string
    public Polynomial(String s) {
        //Guardam dins una variable 1 o -1 per saber el signe que tendrà un monomi
        float signe = 1;
        //Separam el string que ens passen per els espais, i tenim monòmi a monomi
        String[] original = s.split(" ");
        for (String actual : original) {
            //Modificam el signe depenguent del signe que tengui el monomi
            if (actual.equals("+")) {
                signe = 1;
                continue;
            }
            if (actual.equals("-")) {
                signe = -1;
                continue;
            }
            //treim el exponent (explicat dins la funció)
            int exponent = treuExp(actual);
            //treim el coeficient (explicat dins la funció)
            float coeficient = treuCoe(actual) * signe;
            //si el cocient es 0 que passi a la següent iteració ja que no ens interesa
            if (coeficient == 0) {
                continue;
            }
            //col·locam el cocient en la posició que li correspon
            setCoef(coeficient, exponent);
        }
        //giram l'array per tenirlo ordenat de major exponent a menor
        flipIt(this.nums);
    }

    //Funció que simplement ens gira un array
    private void flipIt(float[] p) {
        for (int i = 0; i < p.length / 2; i++) {
            float aux = p[i];
            p[i] = p[p.length - i - 1];
            p[p.length - i - 1] = aux;
        }
    }

    //Ens col·loca el cocient en el lloc que li correspon
    private void setCoef(float coef, int expo) {
        //Primer de tot comprovam que l'array que tenim actualment sigui major que l'exponent que té el numero
        if (expo >= this.nums.length) {
            //si no és aquest cas cream un altre array que tengui la capcitat suficient
            float[] aux = new float[expo + 1];
            //col·locam el cocient all lloc corresponent
            aux[expo] += coef;
            //anam copiant l'array actual per el nou
            for (int i = 0; i < aux.length; i++) {
                if (i <= this.nums.length - 1) {
                    aux[i] += this.nums[i];
                }
            }
            //feim que l'array actual sigui el nou que hem creat
            this.nums = aux;
        } else {
            //en cas contrari simplement sumam el cocient al lloc del exponent per si tenim dos cocients que tenguin el
            //mateix exponent
            this.nums[expo] += coef;
        }
    }

    //Ens treu el exponent
    private int treuExp(String s) {
        //declaram el valor de retorn que per defecte serà 0
        int exp = 0;
        //si tenim una x unicament sabem perfectament que el exponent serà 1
        if (s.contains("x") && !s.contains("^")) {
            exp = 1;
            return exp;
        } else {
            //però en cas contrari hem de saber el que tenim despres del '^' (elevat)
            for (int i = 0; i < s.length(); i++) {
                if (s.charAt(i) == '^') {
                    exp = Integer.parseInt(s.substring(i + 1));
                }
            }
        }
        //retornam el exponent
        return exp;
    }

    //Ens treu el coeficient
    private float treuCoe(String s) {
        //declaram el valor de retorn que per defecte serà 1
        float coe = 1;
        //si és un numero totsol simplement el retornam tal cual és amb la funció de java parseFloat
        if (!s.contains("x") && !s.contains("^")) {
            coe = Float.parseFloat(s);
            return coe;
        } else {
            //en cas contrari hem de saber quin número és i ho feim amb el index desde on es troba el numero fins a la
            //x
            for (int i = 0; i < s.length(); i++) {
                if (s.charAt(i) == 'x' && i > 0) {
                    if (i > 0 && s.charAt(i - 1) == '-') {
                        return -1;
                    }
                    coe = Float.parseFloat(s.substring(0, i));
                }
            }
        }
        //i finalment el retornam
        return coe;
    }

    // Suma el polinomi amb un altre. No modifica el polinomi actual (this). Genera un de nou
    public Polynomial add(Polynomial p) {
        //Miram quin dels dos arrays hem de modificar
        if (this.nums.length != p.nums.length) {
            if (p.nums.length < this.nums.length) {
                p.nums = addNotEquals(p.nums, this.nums);
            } else {
                this.nums = addNotEquals(this.nums, p.nums);
            }
        }
        //Ara que ja tenim els arrays iguals en cream un de nou
        //I anam ficant els valors sumats
        float[] sum = new float[p.nums.length];
        for (int i = 0; i < this.nums.length; i++) {
            sum[i] = p.nums[i] + this.nums[i];
        }
        //Cream un polinomi nou amb els valors sumats i el retornam
        Polynomial sumat = new Polynomial(sum);
        return sumat;
    }

    //Funció que ens allarga un array
    private float[] addNotEquals(float[] p, float[] p1) {
        //Cream el nou array més llarg, i li posam de la longitud de l'altre array i el anam copiant
        float[] arraybe = new float[p1.length];
        for (int i = 0; i < p.length; i++) {
            arraybe[i] = p[p.length - i - 1];
        }
        //el giram ja que estarà amb els valors mal posicionats
        flipIt(arraybe);
        return arraybe;
    }

    // Multiplica el polinomi amb un altre. No modifica el polinomi actual (this). Genera un de nou
    public Polynomial mult(Polynomial p2) {
        //Cream un array nou que tengui la longitud dels dos arrays que tenim,
        float[] mult = new float[p2.nums.length + this.nums.length - 1];
        for (int i = 0; i < this.nums.length; i++) {
            //Guardam el nombre actual que volem multiplicar
            float numactual = this.nums[i];
            if (numactual == 0) {
                continue;
            }
            //Multiplicam aquest numero per tots els del altre array
            for (int j = 0; j < p2.nums.length; j++) {
                mult[j + i] += numactual * p2.nums[j];
            }
        }
        //Ficam l'array amb els nombres multiplicat i el retornam
        Polynomial multiplicat = new Polynomial(mult);
        return multiplicat;
    }

    private Polynomial simpleMult(float num, int pos) {
        float[] res = new float[pos + this.nums.length];
        for (int i = 0; i < this.nums.length; i++) {
            float actual = this.nums[i];
            if (actual == 0) {
                continue;
            }
            res[i] += num * actual;
        }
        return new Polynomial(res);
    }

    //Funció que canvia el signe d'un array
    private void signeChange() {
        //Recorrem un array multiplicant-lo per -1 així canviam el signe
        for (int i = 0; i < this.nums.length; i++) {
            this.nums[i] *= -1;
        }
    }

    // Divideix el polinomi amb un altre. No modifica el polinomi actual (this). Genera un de nou
    // Torna el quocient i també el residu (ambdós polinomis)
    public Polynomial[] div(Polynomial divisor) {
        //Cream dos polinomis que serà el residu, i el cocient
        Polynomial residu = this;
        Polynomial cocient = new Polynomial();
        //La condició de fi serà cuan l'exponent sigui major el del residu o igual i el numero sigui diferent de 0
        while (residu.nums.length-1 >= divisor.nums.length-1 && residu.nums[residu.nums.length-1] != 0) {

            //Treim el cocient, que sempre serà el primer nombre del dividend dividit entre el primer nombre del
            //divisor i el residu serà restar les longituds dels seus arrays
            float rescf = residu.nums[0] / divisor.nums[0];
            int resexp = (residu.nums.length-1) - (divisor.nums.length-1);

            //I ho col·locam al polinomi cocient
            cocient.setCoef(rescf, resexp);

            //Cream un polinomi que serà el que hem de restar amb residu, multimplicant el divisor per el cocient que
            //ens ha sortit
            Polynomial resta = divisor.simpleMult(rescf, resexp);

            //Li canviam el signe
            resta.signeChange();

            //I feim un add al residu, però com que tendrà el signe canviat serà una resta
            residu = residu.add(resta);
        }
        //Un cop tenim fet tot el procediment l'unic que hem de fer es girar l'array del cocient
        //Ficar dins un array de polinomis el cocient que ens resulta amb el residu i retornar-lo
        cocient.flipIt(cocient.nums);
        Polynomial[] divisio = {cocient, residu};
        return divisio;
    }

    // Troba les arrels del polinomi, ordenades de menor a major
    public float[] roots() {
        //Cream l'array que ens guardarà el resultat
        float [] sol;
        //Anam mirant el tipus de polinomi que és
        if (this.nums.length - 1 == 1) {
            //Si unicament és x +/- (numero) cridam a la funció nomésX
            sol = onlyX(this);
            return sol;
            //Si ja tenim un polibomi que es més gran anam mirant el tipus que és
        } else if (this.nums.length - 1 >= 2) {
            if (this.nums.length - 1 == 2) {
                //Resoldre polinomi amb una equació de segon grau
                sol = Arrel(this);
                return sol;
                //En el cas que tinguem un polinomi que sigui biquadràtic ho resoldrem al mètode
            } else if (this.nums.length - 1 > 2) {
                if (this.nums.length - 1 == 4 && this.nums[1] == 0 && this.nums[3] == 0 && this.nums[2] != 0) {
                    sol = biquaD();
                    return sol;
                    //Si tenim un polinomi que sigui x^(numero) +/- nombre ho farem amb un altre mètode
                } else if (this.nums[1] == 0) {
                    sol = Arrelmasdos(this.nums);
                    return sol;
                    //I en el cas que sigui d'un altre tipus ho farem per mètode de ruffini utilitzant els divisors
                    //del darrer numero del polinomi
                } else {
                    float[] divisors = getDivisors(this.nums[this.nums.length - 1]);
                    sol = diferent(divisors);
                    return sol;
                }
            }
        }
        return null;
    }

    //Mètode que ens treu els divisors possibles d'un número
    private float[] getDivisors(float num) {
        //Primer miram la quantitat de divisors que tendrà el número i saber la longitud que serà l'array que retornarem
        //de divisors
        int cont = 0;
        for (int i = 0; i < num; i++) {
            if (num % i == 0) {
                cont++;
            }
        }
        //Desrpés cream un array amb tots els possibles divisors i el retornam
        float[] divisors = new float[cont];
        cont = 0;
        for (int i = 0; i < num; i++) {
            if (num % i == 0) {
                divisors[cont] = i;
                cont++;
            }
        }
        return divisors;
    }

    //Ens lleva els zeros a la part dreta del polinomi
    private void llevazeros() {
        for (int i = 0; i < this.nums.length; i++) {
            if (this.nums[i] == 0) {
                float[] be = new float[i];
                for (int j = 0; j < be.length; j++) {
                    be[j] = this.nums[j];
                }
                this.nums = be;
            }
        }
    }

    //Resol una divisió de polinomis per rufini
    private void ruffini(float num) {
        for (int i = 0; i < this.nums.length; i++) {
            this.nums[i] *= num;
            if (i < this.nums.length - 1) {
                this.nums[i + 1] += this.nums[i];
            } else {
                break;
            }
        }
    }

    //Mètode que ens resoldrà una equació que sigui totalment diferent de les ja utilitzades
    private float[] diferent(float[] divisors) {
        //Cream l'array que retornarem
        float[] sol = new float[4];
        //Provam quin dels divisors funcionarà per fer que de residu ens dongui 0
        for (int i = 0; i < divisors.length; i++) {
            this.ruffini(divisors[i]);
            //Un cop el trobem guardam aquest divisor dins el resultat ja que és una possible sol·lució
            if (this.nums[this.nums.length - 1] == 0) {
                sol[i] = divisors[i];
                //I li llevam els zeros de la part dreta de l'array al polinomi
                llevazeros();
                //Un cop hem arribat a una equació de segon grau, el resoldrem utlitzant el mètode emprat anteriorment
                if (this.nums.length - 1 == 2) {
                    if (solPos(this.nums) > 0) {
                        //Feim l'arrel del polinomi
                        float[] aux = Arrel(this);
                        //Un cop tenim això fet guardam les sol·lucions que ens ha sortit i les retornam
                        sol[2] = aux[0];
                        sol[3] = aux[1];
                        Arrays.sort(sol);
                        return sol;
                    }
                }
            }
        }
        return null;
    }

    //Mètode que ens resol l'arrel d'un polinomi biquadràtic
    private float[] biquaD() {
        float[] sol = this.nums;
        //Cream un nou polinomi, que sigui numX^2 +/- numX +/- num
        float[] aux = {sol[0], sol[2], sol[4]};
        Polynomial bi = new Polynomial(aux);
        //Miram la quantitat de sol·lucions possibles que té el polinomi
        if (solPos(aux) > 0) {
            //Un cop tenim fet això, ja sabem que pot tenir dues sol·lucions, però ja que és biquadràtica en tendrà 4
            sol = new float[4];
            //Resolem l'equació que hem aïllat, ja que és de segon grau
            aux = Arrel(bi);
            //Així ens quedarien 2 sol·lucions possibles i tendriem alguna cosa parescuda a Y = X^2
            //el que hem de fer es X = (Arrel quadrada) Y, i això ens dóna 2 sol·lucions possibles per cada nombre
            double solpos1 = Math.sqrt(aux[0]);
            double solpos2 = Math.sqrt(aux[1]);
            //Un cop tenim les dues sol·lucions possibles les canviam de signe cada terme i també els ficam
            sol[0] = (float) solpos2 * -1;
            sol[1] = (float) solpos1 * -1;
            sol[2] = (float) solpos1;
            sol[3] = (float) solpos2;
            Arrays.sort(sol);
            return sol;
            //En cas contrari que només tengui una sol·lució, realment en tendrà dues, el procediment és el mateix però
            //Amb un unic terme
        } else if (solPos(aux) == 0) {
            sol = new float[2];
            aux = Arrel(bi);
            double solpos1 = Math.sqrt(aux[0]);
            sol[0] = (float) solpos1 * -1;
            sol[1] = (float) solpos1;
            return sol;
        } else {
            return null;
        }
    }

    //Amb aquest mètode miram quantes sol·lucions pot tenir una equació de segon grau
    private int solPos(float[] nums) {
        //Retornam la part de b^2 - 4*a*c
        //I depenguent d'aquest numero sabrem si pot tenir una, dues o cap sol·lució possible
       return (int) ((nums[1] * nums[1]) - (4 * nums[0] * nums[2]));
    }

    //Mètode que ens resol una equació simple
    private float[] onlyX(Polynomial p) {
        //Miram si la x té cualcum número o no, i depenguent si té o no simplement ho resolvem com una equació
        if (this.nums[0] > 1) {
            //Aïllam la x i passam dividint el nombre i restant l'altre nombre
            float[] sol = {p.nums[0] / (p.nums[1] * -1)};
            return sol;
        } else {
            //Unicament tornam elnombre amb el signe canviat
            float[] sol = {p.nums[1] * -1};
            return sol;
        }
    }

    //Mètode que ens resol una arrel majorque 0
    private float[] arrelmasdosprimajor1(double grade, double solpos) {
        //Tenguent el grau i la sol·lució possible, sobretot amb el grau si es par sabem que tendra una o dues sol·lucions
        if (grade % 2 == 0) {
            //Amb Math.pow podem fer una arrel del grau que volguem noltros
            double sol = Math.pow(solpos * -1, 1.0 / grade);
            //Miram que el numero sigui major que 0 i en cas contrari no tendrà possible sol·lució
            if (sol > 0) {
                //Posam les dues possibles sol·lucions dins l'array i el retornam
                float[] retorn = new float[2];
                retorn[0] = (float) sol * -1;
                retorn[1] = (float) sol;
                return retorn;
            } else {
                return null;
            }
        } else {
            //Si unicament té una sol·lució, feim el Math.pow i el retornam
            double sol = Math.pow(solpos, 1.0 / grade);
            float[] retorn = {(float) sol * -1};
            return retorn;
        }
    }

    //Mètode que ens resol una equació del tipus X^numero +/- numero
    private float[] Arrelmasdos(float[] nums) {
        float [] sol;
        //Primer aconseguim el grau del polinomi que tenim i el terme independent
        double grade = nums.length - 1;
        double solpos = nums[nums.length - 1];
        //Si unicament és una X ho farem com si fos una equació normal, aïllant els termes
        if (nums[0] == 1) {
            //Aconseguim el resultat de fer per exemple arrel cubica o major i ho retornam
            sol = arrelmasdosprimajor1(grade, solpos);
            return sol;
        } else if (nums[0] != 1) {
            //Si el numero de la x no és un 1 passa dividint i el mateix procediment
            solpos = nums[0] / solpos * -1;
            sol = arrelmasdosprimajor1(grade, solpos);
            return sol;
        } else {
            return null;
        }
    }

    //Mètode que ens resol una equació de segon grau
    private float[] Arrel(Polynomial p) {
        //Primer miram quantes sol·lucions possibles tendrà el nostre polinomi
        //Un cop mirat això simplement ho hem de resoldre com una equació de segon grau
        double part2 = solPos(p.nums);
        //Si el nombre que ens surt es menor que 0 simplement no tendrà una sol·lució possible
        if (part2 < 0) {
            return null;
            //Si el nombre és 0 tendràuna possible sol·lució
        } else if (part2 == 0) {
            float[] result = {-p.nums[1] / (p.nums[0] * 2)};
            return result;
            //Si el nombre és positiu tendrem dues sol·lucions possibles
        } else {
            part2 = Math.sqrt(part2);
            float[] result = new float[2];
            result[1] = (-p.nums[1] + (float) part2) / (p.nums[0] * 2);
            result[0] = (-p.nums[1] - (float) part2) / (p.nums[0] * 2);
            return result;
        }
    }

    // Torna "true" si els polinomis són iguals. Això és un override d'un mètode de la classe Object
    @Override
    public boolean equals(Object o) {
        //Passam l'object a polinomi
        Polynomial p = (Polynomial) o;
        //Comparam si són iguals amb la funció toString
        return this.toString().equals(p.toString());
    }

    // Torna la representació en forma de String del polinomi. Override d'un mètode de la classe Object
    @Override
    public String toString() {
        //Ja que el nostre constructor no ens deixa tenir zeros a la part dreta de l'array simplement si el primer que
        //Troba es zero que retorni zero i s'ha acabat
        if (this.nums[0] == 0) {
            return "0";
        }
        //En cas contrari hem d'anar mirant el que tenim dins l'array
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < this.nums.length; i++) {
            //Si troba un zero que miri el següent numero
            if (this.nums[i] == 0) {
                continue;
            }
            //Si unicament troba un número que el retorni en forma de String
            if (this.nums.length == 1) {
                return String.valueOf(this.nums[i]);
                //Si en troba dos
            } else if (this.nums.length == 2) {
                //Si és un 1 simplement que posi x o si es -1 que posi -x
                if (this.nums[i] == 1 && i == 0) {
                    sb.append("x");
                } else if (this.nums[i] == -1 && i == 0) {
                    sb.append("-x");
                    //En cas contrari que posi el numero que és mes la x
                } else if (this.nums[i] != 1 && i == 0) {
                    sb.append((int) this.nums[i] + "x");
                    //Si ja hem mirat la part de la x que ens posi el número depenguent del signe que tengui
                } else if (this.nums[i] != 1 && this.nums[i] != 0 && this.nums[i] > 0 && i > 0) {
                    sb.append(" + " + (int) this.nums[i]);
                } else if (this.nums[i] != 1 && this.nums[i] != 0 && this.nums[i] < 0 && i > 0) {
                    sb.append(" - " + (int) this.nums[i] * - 1);
                }
                //Aquí ja miram si l'array és major a longitud 2 és a dir que ja comença a haver-hi x
            } else if (this.nums.length > 2) {
                //Si és el primer nombre que estam mirant depenguent si és un 1 o -1 o altre nombre
                if (i == 0) {
                    //Li posarem la x o -x o nombre x ^ a la longitud que té l'array - 1 així aconseguirem el grau
                    //Corresponent
                    if (this.nums[i] == 1) {
                        sb.append("x^" + (this.nums.length - 1));
                    } else if (this.nums[i] == -1) {
                        sb.append("-x^" + (this.nums.length - 1));
                    } else if (this.nums[i] < 1 || this.nums[i] > 1) {
                        sb.append((int) this.nums[i] + "x^" + (this.nums.length - 1));
                    }
                    //Aquí ja estam mirant a partir de la posició 1 fins a la antepenúltima de l'array
                } else if (i > 0) {
                    //I depenguent del que tinguem posarem o -/+ X o el nombre X^ a la longitud-1-i així tendrem el grau
                    //que li toca
                    if (this.nums.length - i - 1 > 1) {
                        if (this.nums[i] == 1) {
                            sb.append(" + x^" + (this.nums.length - 1 - i));
                        } else if (this.nums[i] == -1) {
                            sb.append(" - x^" + (this.nums.length - 1 - i));
                        } else if (this.nums[i] > 1) {
                            sb.append((" + " + (int) this.nums[i] + "x^" + (this.nums.length - 1 - i)));
                        } else if (this.nums[i] < 1) {
                            sb.append((" - " + (int) this.nums[i] * -1 + "x^" + (this.nums.length - 1 - i)));
                        }
                        //Aquí miram a lo que li vendría corresponent al numero i x sense elevat
                    } else if (this.nums.length - i - 1 == 1) {
                        //Sempre és el mateix procediment, si és 1 o -1 o altre numero
                        if (this.nums[i] == 1) {
                            sb.append(" + x");
                        } else if (this.nums[i] == -1) {
                            sb.append(" - x");
                        } else if (this.nums[i] > 1) {
                            sb.append(" + " + (int) this.nums[i] + "x");
                        } else if (this.nums[i] < 1) {
                            sb.append(" - " + (int) this.nums[i] * -1 + "x");
                        }
                        //I per acabar simplement el darrer nombre que el ficam com és
                    } else if (this.nums.length - i - 1 == 0) {
                        if (this.nums[i] == 1) {
                            sb.append(" + 1");
                        } else if (this.nums[i] == -1) {
                            sb.append(" - 1");
                        } else if (this.nums[i] > 1) {
                            sb.append(" + " + (int) this.nums[i]);
                        } else if (this.nums[i] < 1) {
                            sb.append(" - " + (int) this.nums[i] * -1);
                        }
                    }
                }
            }
        }
        return sb.toString();
    }
}