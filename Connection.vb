Imports System.Data.SqlClient
Imports System.Security.Cryptography
Imports System.Text

Module Connection
    Public lastForm As Form

    Public conn As SqlConnection
    Public command As SqlCommand
    Public reader As SqlDataReader
    Public adapter As SqlDataAdapter
    Public dataset As New DataSet

    Public query, sessionId, sessionRole, sessionName As String

    Public connString As String = "Data Source = ROOGRY\SQLEXPRESS; Initial Catalog = latihan; Integrated Security = true; MultipleActiveResultSets = true"

    Public Sub openConn()
        Try
            conn = New SqlConnection(connString)
            If conn.State <> ConnectionState.Closed Then
                conn.Close()
            Else
                conn.Open()
            End If
        Catch ex As Exception
            MsgBox(ex.Message & ex.StackTrace)
        End Try
    End Sub

    Public Function validation(sender As Form) As Boolean
        Dim listCtrl As String = ""
        Dim jml As Integer = 0

        For Each ctrl As Control In sender.Controls
            If (ctrl.GetType Is GetType(TextBox) Or ctrl.GetType Is GetType(ComboBox)) And ctrl.AccessibleName <> "noValid" Then
                If ctrl.Text = "" Then
                    jml += 1
                    listCtrl &= ctrl.AccessibleName & ", "
                End If
            End If
        Next

        If jml > 0 Then
            MsgBox(listCtrl & "Tidak boleh kosong")
            Return False
        Else
            Return True
        End If
    End Function

    Public Function SHA512(text As String)
        Dim a As Byte() = UnicodeEncoding.UTF8.GetBytes(text)
        Dim b As Byte()
        Dim c As New SHA512Managed

        b = c.ComputeHash(a)

        Dim d As String = BitConverter.ToString(b)
        d = d.Replace("-", "")
        Return d.ToLower
    End Function

    Public Function employeeId() As String
        Dim Id As String = "EM"
        Dim lastId As Integer = 0

        Try
            openConn()
            query = "SELECT TOP 1 Id FROM MsEmployee Order BY Id DESC"
            Command = New SqlCommand(query, conn)
            reader = Command.ExecuteReader

            If reader.Read Then
                lastId = Convert.ToInt32(Mid(reader(0), 3)) + 1
                If lastId < 1000 Then
                    For i As Integer = 0 To 3 - lastId.ToString.Length
                        Id &= "0"
                    Next
                End If
                Return Id & lastId
            Else
                Return "EM0001"
            End If
        Catch ex As Exception

        End Try
    End Function

    Public Function orderId() As String
        Dim id As String = DateTime.Now.ToString("yyyyMMdd")
        Dim i As Integer = 0
        Try
            openConn()
            query = "SELECT TOP 1 Id FROM OrderHeader WHERE Id = '" & id & "' Order By Id Desc"
            command = New SqlCommand(query, conn)
            reader = Command.ExecuteReader
            If reader.Read Then
                i = Convert.ToInt32(Mid(reader(0), 9)) + 1
                If i < 1000 Then
                    For x As Integer = 0 To 3 - i.ToString.Length
                        id &= "0"
                    Next
                End If
                Return id & i
            Else
                Return id & "0001"
            End If
        Catch ex As Exception
            MsgBox(ex.Message & ex.StackTrace)
        End Try
    End Function

    Public Function nextId(table As String) As String
        Try
            openConn()
            query = "SELECT TOP 1 Id FROM " & table & " Order By Id Desc"
            Command = New SqlCommand(query, conn)
            reader = Command.ExecuteReader
            If reader.Read Then
                Return reader(0) + 1
            Else
                Return "1"
            End If
        Catch ex As Exception
            MsgBox(ex.Message & ex.StackTrace)
        End Try
    End Function
End Module
