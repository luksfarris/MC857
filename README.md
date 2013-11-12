MC857
=====

>Cálculo da integralização de alunos da UNICAMP. Servidor de dados em Python e aplicativo móvel para Android.


Etapas
=====
>
[X] Modelagem do Sistema
>
[X] Definição dos contratos
>
[X] Revisão da modelagem e contratos
>
[X] Construção (implementação)
>
[X] Testes
>
[X] Entrega Final

Instalando Python
=====
>
Faça o download do Python 2.7 em: http://www.python.org/ftp/python/2.7.5/python-2.7.5.msi (32-bits) ou http://www.python.org/ftp/python/2.7.5/python-2.7.5.amd64.msi (64-bits)
>
No passo que ele pergunta se quer salvar para todos usuários ou só o seu, escolha apenas o seu

Rodando o servidor
=====
>
Faça o download do .rar do servidor do professor: http://www.ic.unicamp.br/~vanini/mc857/servidorDeDados.zip
>
Abra um terminal (como adm no windows) e navegue até a pasta do servidor
>
Execute: "Python servidorDeDados.py", caso fale que Python não é um comando execute o Python diretamente (C:\Python2.7\python.exe servidorDeDadps.py) ou configure as variáveis de ambiente.
>
Caso ele dê outro erro 10030, ou algo do tipo, é a porta 80 do seu computador já sendo usada, edite o arquivo servidorDeDados.py e coloque 81, ou alguma superior até funcionar. Quando funcionar uma tela abrirá pedindo para liberar o acesso,.

Exemplo de uso
=====
>
Para usar, precisamos passar qual tipo de serviço queremos, eles estão listados no dados.py
>
Exemplo: "http://localhost:81//?serv=c&cod=34"
