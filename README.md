# Sistem-Energetic

Pasca Mihai Alexandru - 321CA

Etapa 1

In implementarea problemei am folosit design pattern-ul "Factory" pentru crearea
entitatilor (ConcreteConsumer si ConcreteDistributor) si "Singleton" pentru
crearea simularii jocului (GameSimulatioon) si pentru crearea entitatilor 
(FactoryProvider). Mecanismul implementarii functioneaza astfel: ConcreteConsumer
si ConcreteDistributor extind clasele abstracte Consumer respectiv Distributor,
care sunt folosite pentru abstractizarea Factory-urilor. Astfel, proiectul poate
fi extins in cazul in care vrem sa adaugam mai multe tipuri de Consumer/Distributor.
ConsumerFactory si DistributorFactory implementeaza interfata EntityFactory, care
este folosita pentru abstractizarea FactoryProvider. Astfel, proiectul paote fi 
extins in cazul in care vrem sa adaugam mai multe tipuri de entitati. Atunci cand
se creaza un contract, prin intermediul clasei Contract, acesta este salvat in
Consumer dar si in lista instantei Distributor si este folosit pentru efectuarea
platii lunare pe care o face Consumer-ul la distributor. Consumerul si Distributorul 
contin metode prin care se fac platile lunare aferente acestor tipuri de entitati.
In clasa Repo se retin datele necesare simularii, iar in constructor se transforma
datele din clasele de input in cele folosite pentru simulare prin intermediul
factory-urilor.

Implementarea incepe cu citirea input-ului cu ajutorul claselor InputLoader,
InputData, EntityInput, MonthlyInputData, CostChange. In clasa main se obtine
instanta de GameSimulation, se executa initializarea bazei de date si se 
parcurge runda initiala, dupa care se incepe parcurgerea rundelor, updatand
baza de date inaintea inceperii fiecarei runde. La sfarsitul se va scrie in 
fisierul de iesire starea entitatilor la sfarsitul simularii, cu ajutorul
clasei OutputWriter.