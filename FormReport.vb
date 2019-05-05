Imports System.Data.SqlClient

Public Class FormReport
    Private Sub btnGen_Click(sender As Object, e As EventArgs) Handles btnGen.Click
        If validation(Me) Then
            If cbFrom.SelectedIndex < cbTo.SelectedIndex Then
                Try
                    openConn()
                    query = "SELECT DATENAME(MONTH, DATEADD(MONTH, MONTH(Date), -1)) 'Month', SUM(Qty*Price)/1000000 'Income' FROM OrderDetail a INNER JOIN OrderHeader b ON a.OrderId = b.Id INNER JOIN MsMenu c ON a.MenuId = c.Id WHERE PaymentType IS NOT NULL And MONTH(Date) BETWEEN '" & cbFrom.SelectedIndex + 1 & "' AND '" & cbTo.SelectedIndex + 1 & "' GROUP BY MONTH(Date) ORDER BY MONTH(Date)"

                    adapter = New SqlDataAdapter(query, conn)
                    dataset.Clear()
                    adapter.Fill(dataset, "report")
                    With dg
                        .DataSource = dataset
                        .DataMember = "report"
                    End With

                    report.Series("Income").Points.Clear()

                    For i As Integer = 0 To dg.Rows.Count - 1
                        report.Series("Income").Points.AddXY(dg.Item(0, i).Value, dg.Item(1, i).Value)
                    Next



                Catch ex As Exception
                    MsgBox(ex.Message & ex.StackTrace)
                End Try
            Else
                MsgBox("Pilihlah bulan yang lebih kecil ke besar")
            End If
        End If

    End Sub

    Private Sub FormReport_FormClosed(sender As Object, e As FormClosedEventArgs) Handles Me.FormClosed
        NavAdmin.Show()
    End Sub
End Class