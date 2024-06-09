Imports System.Data.OleDb
Module modConnection
    Public cn As New OleDb.OleDbConnection
    Public cm As New OleDb.OleDbCommand
    Public dr As OleDbDataReader

    Public Sub Connection()
        cn = New OleDb.OleDbConnection
        With cn
            .ConnectionString = "Provider=Microsoft.ACE.OLEDB.12.0;Data Source=C:\Users\suray\source\repos\REVISION PT2\REV2.accdb"

            .Open()
        End With
    End Sub

End Module
