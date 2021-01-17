# Sistem-Energetic

Pasca Mihai Alexandru - 321CA

## Etapa 1

### Design patterns

In implementarea problemei am folosit design pattern-ul "Factory" pentru crearea
entitatilor (ConcreteConsumer si ConcreteDistributor) si "Singleton" pentru
crearea simularii jocului (GameSimulatioon) si pentru crearea entitatilor 
(FactoryProvider). 

### Entitati

Mecanismul implementarii functioneaza astfel: ConcreteConsumer
si ConcreteDistributor extind clasele abstracte Consumer respectiv Distributor,
care sunt folosite pentru abstractizarea Factory-urilor. Astfel, proiectul poate
fi extins in cazul in care vrem sa adaugam mai multe tipuri de Consumer/Distributor.
ConsumerFactory si DistributorFactory implementeaza interfata EntityFactory, care
este folosita pentru abstractizarea FactoryProvider. Astfel, proiectul poate fi 
extins in cazul in care vrem sa adaugam mai multe tipuri de entitati. Atunci cand
se creaza un contract, prin intermediul clasei Contract, acesta este salvat in
Consumer dar si in lista instantei Distributor si este folosit pentru efectuarea
platii lunare pe care o face Consumer-ul la distribuitor. Consumerul si Distributorul 
contin metode prin care se fac platile lunare aferente acestor tipuri de entitati.
In clasa Repo se retin datele necesare simularii, iar in constructor se transforma
datele din clasele de input in cele folosite pentru simulare prin intermediul
factory-urilor.

### Flow
Implementarea incepe cu citirea input-ului cu ajutorul claselor InputLoader,
InputData, EntityInput, MonthlyInputData, CostChange. In clasa main se obtine
instanta de GameSimulation, se executa initializarea bazei de date si se 
parcurge runda initiala, dupa care se incepe parcurgerea rundelor, updatand
baza de date inaintea inceperii fiecarei runde. La sfarsit se va scrie in 
fisierul de iesire starea entitatilor la sfarsitul simularii, cu ajutorul
clasei OutputWriter.

## Etapa 2

### Entitati
Au fost adaugate clasele ConcreteProducer si Producer, care se ocupa de operatiile
pe care producatorii le-au de facut lunar, iar clasa Stats se ocupa de datele 
pe care acestia le retin lunar. Clasa Producer este folosita pentru abstractizarea 
factory-urilor, astfel proiectul poate fi extins in cazul in care apar mai multe
tipuri de producatori. ProducerFactory implementeaza interfata EntityFactory si
este folosit pentru crearea instantelor de producatori. EnergyType este folosit
pentru a pastra tipurile de energie pe care le au producatorii. ProducerChanges
este clasa folosita pentru input-ul de update-uri lunare ale producatorilor.
Clasele din package-ul strategies se ocupa de strategiile distribuitorilor de 
alegere a producatorilor. Astfel, EnergyChoiceStrategyFactory este folosit pentru
crearea strategiilor atunci cand trebuie facuta alegerea dintre GreenStrategy
(priotate pentru energie renewable), PriceStrategy (prioritate pentru pret mai mic),
QuantityStrategy (proritate pentru cantitate mai mare).

### Flow
Clasa Main se ocupa de controlarea flow-ului, iar in clasa GameSimulation este 
implementatea logica simularii. Astfel, se citeste input-ul intr-o instanta 
InputData folosind clasa InputLoader, iar acesta este trimis clasei de simulare
pentru a initializa repository-ul cu date, dupa care se ruleaza task-urile rundei
initiale. Apoi in fiecare luna, se updateaza consumatorii si distribuitorii in baza
de date, se ruleaza task-urile rundei, se updateaza producatorii in baza de date,
dupa care se ruleaza task-urile finalului de runda. Flow-ul rundei este urmatorul:
* distribuitorii noi isi aleg producatorii
* se cauta in baza de date cel mai ieftin distribuitor
* consumatorii isi aleg distribuitor si il platesc
* distribuitorii isi platesc costurile. 
  

Flow-ul finalului rundei este urmatorul:
* distribuitorii isi realeg producatorii daca e cazul
* se salveaza datele lunare ale producatorilor

La sfarsit se va scrie in fisierul de iesire starea entitatilor la sfarsitul simularii,
cu ajutorul clasei OutputWriter, care preia datele din repository.

### Design patterns

Am folosit pattern-ul "Observer" pentru a controla legatura dintre producatori si
distribuitori. Astfel, distribuitorii sunt Observers, producatorii sunt
Observables, iar acestia isi notifica obseravatorii in momentul in care primesc
un update pentru ca distribuitorii sa isi realeaga producatorii.
Am folosit pattern-ul "Strategy" pentru alegerea producatorilor de catre 
distribuitori, acestia adoptand una dintre strategii: Green, Price si Quantity,
pe care o obtin in momentul in care aceasta trebuie aplicata prin intermediul
unui factory.
